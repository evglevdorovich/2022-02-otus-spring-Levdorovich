package ru.otus.library.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.library.domain.Book;
import ru.otus.library.domain.Comment;
import ru.otus.library.dto.CommentDto;
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

    @WithMockUser(
            username = "user",
            authorities = {"ROLE_USER"}
    )
    @Test
    @DisplayName("return comments page")
    void shouldReturnCommentsPage() throws Exception {
        var book = new Book();
        book.setName("bookName");
        book.setId(1);
        var comment = new Comment(1, "commentText", book);
        var commentDto = new CommentDto();
        commentDto.setId(comment.getId());
        commentDto.setText(comment.getText());

        given(commentService.getByBookId(book.getId())).willReturn(List.of(comment));
        given(modelMapper.map(comment, CommentDto.class)).willReturn(commentDto);

        mvc.perform(get("/comments/1"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(commentDto.getText())))
                .andExpect(content().string(containsString(Long.toString(commentDto.getId()))));
    }

}