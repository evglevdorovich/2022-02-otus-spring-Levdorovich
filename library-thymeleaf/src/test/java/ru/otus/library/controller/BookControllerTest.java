package ru.otus.library.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.library.domain.Author;
import ru.otus.library.domain.Book;
import ru.otus.library.domain.Genre;
import ru.otus.library.services.AuthorService;
import ru.otus.library.services.BookService;
import ru.otus.library.services.GenreService;

import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(BookController.class)
@DisplayName("Book Controller should")
class BookControllerTest {
    @Autowired
    private MockMvc mvc;
    @MockBean
    private AuthorService authorService;
    @MockBean
    private GenreService genreService;
    @MockBean
    private BookService bookService;

    @WithMockUser(
            username = "user",
            authorities = {"ROLE_USER"}
    )
    @DisplayName("correct show display book")
    @Test
    void shouldCorrectDisplayBook() throws Exception {
        long bookId = 1;
        var genre = new Genre(1, "genreName");
        var author = new Author(1, "authorName");
        var book = new Book(bookId, "name", author, genre);

        given(bookService.getById(bookId)).willReturn(book);
        given(genreService.getAllExceptBooksGenre(book)).willReturn(List.of(genre));
        given(authorService.getAllExceptBooksAuthor(book)).willReturn(List.of(author));

        mvc.perform(get("/books/1"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(author.getName())))
                .andExpect(content().string(containsString(genre.getName())))
                .andExpect(content().string(containsString(book.getName())))
                .andExpect(content().string(containsString(Long.toString(book.getId()))));
    }

    @WithMockUser(
            username = "user",
            authorities = {"ROLE_USER"}
    )
    @DisplayName("correct edit book")
    @Test
    void shouldCorrectEditBook() throws Exception {
        var genreId = "1";
        var authorId = "1";
        var bookName = "bookName";
        mvc.perform(post("/books/edit/1")
                        .param("name", bookName)
                        .param("genreId", genreId)
                        .param("authorId", authorId)
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/books/1"));
        verify(bookService).update(1, bookName, Long.parseLong(genreId), Long.parseLong(authorId));
    }

    @WithMockUser(
            username = "user",
            authorities = {"ROLE_USER"}
    )
    @DisplayName("correct create book")
    @Test
    void shouldCorrectCreateBook() throws Exception {
        var authorId = "1";
        var genreId = "1";
        var bookName = "bookName";
        mvc.perform(post("/books")
                        .param("name", bookName)
                        .param("authorId", authorId)
                        .param("genreId", genreId)
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
        verify(bookService).insert(bookName, Long.parseLong(genreId), Long.parseLong(authorId));
    }

    @WithMockUser(
            username = "user",
            authorities = {"ROLE_USER"}
    )
    @DisplayName("correct delete book")
    @Test
    void shouldCorrectDeleteBook() throws Exception {
        var bookId = 1;
        mvc.perform(post("/books/delete/1")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
        verify(bookService).deleteById(bookId);
    }

}
