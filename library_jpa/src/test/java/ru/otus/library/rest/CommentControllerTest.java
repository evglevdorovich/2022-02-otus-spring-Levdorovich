package ru.otus.library.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.library.domain.Book;
import ru.otus.library.domain.Comment;
import ru.otus.library.dto.CommentDto;
import ru.otus.library.rest.CommentController;
import ru.otus.library.services.CommentService;

import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CommentController.class)
@DisplayName("Comment controller should")
class CommentControllerTest {

    @Autowired
    private MockMvc mvc;
    @MockBean
    private CommentService commentService;
    @MockBean
    private ModelMapper modelMapper;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("return comments")
    void shouldReturnExpectedComments() throws Exception {
        var book = new Book();
        book.setName("bookName");
        book.setId(1);
        var comment = new Comment(1, "commentText", book);
        var commentDto = new CommentDto();
        commentDto.setId(comment.getId());
        commentDto.setText(comment.getText());

        given(commentService.getByBookId(book.getId())).willReturn(List.of(comment));
        given(modelMapper.map(comment, CommentDto.class)).willReturn(commentDto);

        var expectedJson = objectMapper.writeValueAsString(List.of(commentDto));

        mvc.perform(get("/api/comments/1"))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));
    }

}