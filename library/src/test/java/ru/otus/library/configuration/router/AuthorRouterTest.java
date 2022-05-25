package ru.otus.library.configuration.router;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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
import ru.otus.library.handler.AuthorHandler;
import ru.otus.library.repository.AuthorRepository;

import javax.annotation.PostConstruct;
import java.util.List;

import static org.mockito.Mockito.when;

@SpringBootTest(classes = {AuthorRouter.class, ObjectMapper.class, AuthorHandler.class})
@DisplayName("Author Router should")
class AuthorRouterTest {
    @Autowired
    private RouterFunction<ServerResponse> authorsRoute;
    @MockBean
    private AuthorRepository authorRepository;
    @Autowired
    private ObjectMapper objectMapper;

    private WebTestClient client;

    @PostConstruct
    private void init() {
        client = WebTestClient.bindToRouterFunction(authorsRoute).build();
    }

    @Test
    @DisplayName("should return correct author's response")
    void shouldReturnCorrectAuthorsResponse() throws JsonProcessingException {
        val uriPattern = "/api/authors";
        val authors = List.of(new Author("1", "name"));
        val authorFlux = Flux.just(authors.get(0));
        val jsonAuthor = objectMapper.writeValueAsString(authors);

        when(authorRepository.findAll()).thenReturn(authorFlux);

        client.get()
                .uri(uriPattern)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody().json(jsonAuthor);
    }

}
