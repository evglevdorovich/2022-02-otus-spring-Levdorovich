package ru.otus.library.configuration.router;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Hooks;
import ru.otus.library.domain.Author;
import ru.otus.library.domain.Book;
import ru.otus.library.domain.Comment;
import ru.otus.library.domain.Genre;
import ru.otus.library.dto.CommentDto;
import ru.otus.library.handler.CommentHandler;
import ru.otus.library.handler.GenreHandler;
import ru.otus.library.repository.CommentRepository;
import ru.otus.library.repository.GenreRepository;

import javax.annotation.PostConstruct;
import java.util.List;

import static org.mockito.Mockito.when;

@SpringBootTest(classes = {CommentRouter.class, ObjectMapper.class, CommentHandler.class})
@DisplayName("Comment Router should")
public class CommentRouterTest {

    @Autowired
    private RouterFunction<ServerResponse> commentsRoute;
    @MockBean
    private CommentRepository commentRepository;
    @MockBean
    private ModelMapper modelMapper;
    @Autowired
    private ObjectMapper objectMapper;

    private WebTestClient client;

    @PostConstruct
    private void init() {
        client = WebTestClient.bindToRouterFunction(commentsRoute).build();
        Hooks.onOperatorDebug();
    }

    @Test
    void shouldReturnCorrectCommentResponse() throws JsonProcessingException {
        val bookId = "bookId";
        val uriPattern = "/api/comments/" + bookId;
        val commentId = "commentId";
        val book = new Book(bookId, "name", new Author("authorName"),new Genre("genreName"));
        val comments = List.of(new Comment(commentId,"testComment", book));
        val commentsFlux = Flux.just(comments.get(0));

        val commentDto = new CommentDto();

        when(commentRepository.findByBookId(bookId)).thenReturn(commentsFlux);

        commentDto.setText(comments.get(0).getText());
        commentDto.setId(comments.get(0).getId());
        when(modelMapper.map(comments.get(0), CommentDto.class)).thenReturn(commentDto);
        val jsonComments = objectMapper.writeValueAsString(List.of(commentDto));

        client.get()
                .uri(uriPattern)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody().json(jsonComments);
    }

}
