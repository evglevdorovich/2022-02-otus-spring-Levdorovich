package ru.otus.library.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.library.services.AuthorService;
import ru.otus.library.services.BookService;
import ru.otus.library.services.CommentService;
import ru.otus.library.services.GenreService;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest({LibraryController.class, CommentController.class, BookController.class})
@DisplayName("Unauthenticated user should: ")
@MockBean({ModelMapper.class, AuthorService.class, GenreService.class, BookService.class, CommentService.class})
class UnauthenticatedController {
    @Autowired
    private MockMvc mvc;

    @DisplayName("return forbidden")
    @Test
    void unauthenticatedUserShouldReturnForbidden() throws Exception {
        var authorId = "1";
        var genreId = "1";
        var bookName = "bookName";

        mvc.perform(post("/books/delete/1")
                        .with(csrf()))
                .andExpect(status().isUnauthorized());
        mvc.perform(get("/books1"))
                .andExpect(status().isUnauthorized());

        mvc.perform(post("/books/edit/1")
                        .param("name", bookName)
                        .param("genreId", genreId)
                        .param("authorId", authorId)
                        .with(csrf()))
                .andExpect(status().isUnauthorized());

        mvc.perform(post("/books/delete/1")
                        .with(csrf()))
                .andExpect(status().isUnauthorized());

        mvc.perform(post("/books")
                        .param("name", bookName)
                        .param("authorId", authorId)
                        .param("genreId", genreId)
                        .with(csrf()))
                .andExpect(status().isUnauthorized());

        mvc.perform(get("/comments/1"))
                .andExpect(status().isUnauthorized());

        mvc.perform(get("/"))
                .andExpect(status().isUnauthorized());
    }
}
