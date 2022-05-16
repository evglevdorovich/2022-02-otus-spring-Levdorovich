package ru.otus.library.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.library.domain.Author;
import ru.otus.library.domain.Book;
import ru.otus.library.domain.Genre;
import ru.otus.library.dto.BookForViewDto;
import ru.otus.library.services.AuthorService;
import ru.otus.library.services.BookService;
import ru.otus.library.services.GenreService;

import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LibraryController.class)
@DisplayName("Library controller should")
class LibraryControllerTest {
    @Autowired
    private MockMvc mvc;
    @MockBean
    private AuthorService authorService;
    @MockBean
    private GenreService genreService;
    @MockBean
    private BookService bookService;
    @MockBean
    private ModelMapper modelMapper;

    @Test
    @DisplayName("return library page")
    void shouldReturnLibraryPage() throws Exception {
        var author = new Author(1, "authorName");
        var genre = new Genre(1, "genreName");
        var authors = List.of(author);
        var genres = List.of(genre);
        var book = new Book("bookName", author, genre);
        var books = List.of(book);
        var bookDto = new BookForViewDto();
        bookDto.setAuthorName(book.getAuthor().getName());
        bookDto.setGenreName(book.getAuthor().getName());
        bookDto.setId(1);
        bookDto.setName(book.getName());

        given(authorService.getAll()).willReturn(authors);
        given(genreService.getAll()).willReturn(genres);
        given(bookService.getAll()).willReturn(books);
        given(modelMapper.map(book, BookForViewDto.class)).willReturn(bookDto);

        mvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(author.getName())))
                .andExpect(content().string(containsString(genre.getName())))
                .andExpect(content().string(containsString(bookDto.getName())));
    }

}