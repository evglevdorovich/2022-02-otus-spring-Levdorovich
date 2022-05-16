package ru.otus.library.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.library.domain.Author;
import ru.otus.library.domain.Book;
import ru.otus.library.domain.Genre;
import ru.otus.library.dto.BookForUpdateDto;
import ru.otus.library.services.AuthorService;
import ru.otus.library.services.BookService;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookController.class)
@DisplayName("Books controller should")
class BookControllerTest {

    @Autowired
    private MockMvc mvc;
    @MockBean
    private AuthorService authorService;
    @MockBean
    private BookService bookService;
    @Autowired
    private ObjectMapper objectMapper;

    @DisplayName("correct show display book")
    @Test
    void shouldCorrectDisplayBook() throws Exception {
        long bookId = 2;
        var genre = new Genre(1, "genreName");
        var author = new Author(1, "authorName");
        var book = new Book(bookId, "name", author, genre);

        given(bookService.getById(bookId)).willReturn(book);

        var expectedJson = objectMapper.writeValueAsString(book);

        mvc.perform(get("/api/books/2"))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));
    }

    @DisplayName("correct show display books")
    @Test
    void shouldCorrectDisplayBooks() throws Exception {
        long bookId = 1;
        long secondBookId = 2;
        var genre = new Genre(1, "genreName");
        var author = new Author(1, "authorName");
        var book = new Book(bookId, "name", author, genre);
        var secondGenre = new Genre(2, "genreName2");
        var secondAuthor = new Author(2, "authorName2");
        var secondBook = new Book(secondBookId, "name2", secondAuthor, secondGenre);
        var books = List.of(book, secondBook);

        given(bookService.getAll()).willReturn(books);

        var expectedJson = objectMapper.writeValueAsString(books);

        mvc.perform(get("/api/books"))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));
    }

    @DisplayName("correct edit book")
    @Test
    void shouldCorrectEditBook() throws Exception {
        var genreId = 1;
        var authorId = 1;
        var bookName = "bookName";
        var bookForUpdateDto = new BookForUpdateDto();
        bookForUpdateDto.setName(bookName);
        bookForUpdateDto.setAuthorId(authorId);
        bookForUpdateDto.setGenreId(genreId);

        var contentBody = objectMapper.writeValueAsString(bookForUpdateDto);
        mvc.perform(put("/api/books/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(contentBody))
                .andExpect(status().isOk());
        verify(bookService).update(1, bookName, genreId, authorId);
    }

    @DisplayName("correct create book")
    @Test
    void shouldCorrectCreateBook() throws Exception {
        var genreId = 1;
        var authorId = 1;
        var bookName = "bookName";
        var bookForUpdateDto = new BookForUpdateDto();
        bookForUpdateDto.setName(bookName);
        bookForUpdateDto.setAuthorId(authorId);
        bookForUpdateDto.setGenreId(genreId);

        var contentBody = objectMapper.writeValueAsString(bookForUpdateDto);
        mvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(contentBody))
                .andExpect(status().isOk());
        verify(bookService).insert(bookName, genreId, authorId);
    }

    @DisplayName("correct delete book")
    @Test
    void shouldCorrectDeleteBook() throws Exception {
        var bookId = 1;

        mvc.perform(delete("/api/books/1"))
                .andExpect(status().isOk());
        verify(bookService).deleteById(bookId);
    }


}