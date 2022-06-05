package ru.otus.library.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.library.domain.*;
import ru.otus.library.dto.BookForViewDto;
import ru.otus.library.dto.CommentDto;
import ru.otus.library.security.ApplicationUserService;
import ru.otus.library.services.AuthorService;
import ru.otus.library.services.BookService;
import ru.otus.library.services.CommentService;
import ru.otus.library.services.GenreService;

import java.util.Collection;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@WebMvcTest({LibraryController.class, CommentController.class, BookController.class})
@DisplayName("Unauthenticated user should: ")
class SecurityControllerTest {
    @Autowired
    private MockMvc mvc;
    @MockBean
    private ModelMapper modelMapper;
    @MockBean
    private AuthorService authorService;
    @MockBean
    private GenreService genreService;
    @MockBean
    private BookService bookService;
    @MockBean
    private CommentService commentService;


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

    @WithMockUser(
            username = "user",
            authorities = {"ROLE_USER"}
    )
    @Test
    void authenticatedUsersShouldReturnExpectedStatus() throws Exception {
        var author = new Author(1, "authorName");
        var genre = new Genre(1, "genreName");
        var genreId = "1";
        var authorId = "1";

        var authors = List.of(author);
        var genres = List.of(genre);

        var bookName = "bookName";
        var book = new Book(bookName, author, genre);
        var books = List.of(book);
        var bookDto = new BookForViewDto();
        var bookForComment = new Book();
        var bookId = 1L;

        bookForComment.setName("bookName");
        bookForComment.setId(bookId);
        bookDto.setAuthorName(book.getAuthor().getName());
        bookDto.setGenreName(book.getAuthor().getName());
        bookDto.setId(bookId);
        bookDto.setName(book.getName());

        var comment = new Comment(1, "commentText", bookForComment);
        var commentDto = new CommentDto();
        commentDto.setId(comment.getId());
        commentDto.setText(comment.getText());

        given(authorService.getAll()).willReturn(authors);
        given(genreService.getAll()).willReturn(genres);
        given(bookService.getAll()).willReturn(books);
        given(modelMapper.map(book, BookForViewDto.class)).willReturn(bookDto);

        mvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(author.getName())))
                .andExpect(content().string(containsString(genre.getName())))
                .andExpect(content().string(containsString(bookDto.getName())));

        given(commentService.getByBookId(bookForComment.getId())).willReturn(List.of(comment));
        given(modelMapper.map(comment, CommentDto.class)).willReturn(commentDto);

        mvc.perform(get("/comments/1"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(commentDto.getText())))
                .andExpect(content().string(containsString(Long.toString(commentDto.getId()))));

        given(bookService.getById(bookId)).willReturn(book);
        given(genreService.getAllExceptBooksGenre(book)).willReturn(List.of(genre));
        given(authorService.getAllExceptBooksAuthor(book)).willReturn(List.of(author));

        mvc.perform(get("/books/1"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(author.getName())))
                .andExpect(content().string(containsString(genre.getName())))
                .andExpect(content().string(containsString(book.getName())))
                .andExpect(content().string(containsString(Long.toString(book.getId()))));

        mvc.perform(post("/books/edit/1")
                        .param("name", bookName)
                        .param("genreId", genreId)
                        .param("authorId", authorId)
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/books/1"));
        verify(bookService).update(1, bookName, Long.parseLong(genreId), Long.parseLong(authorId));

        mvc.perform(post("/books")
                        .param("name", bookName)
                        .param("authorId", authorId)
                        .param("genreId", genreId)
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
        verify(bookService).insert(bookName, Long.parseLong(genreId), Long.parseLong(authorId));

        mvc.perform(post("/books/delete/1")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
        verify(bookService).deleteById(bookId);
    }
}
