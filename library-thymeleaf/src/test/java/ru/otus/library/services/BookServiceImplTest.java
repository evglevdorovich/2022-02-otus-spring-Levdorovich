package ru.otus.library.services;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.test.context.support.WithMockUser;
import ru.otus.library.domain.Author;
import ru.otus.library.domain.Book;
import ru.otus.library.domain.Genre;
import ru.otus.library.exceptions.EmptyResultException;
import ru.otus.library.exceptions.InvalidDataForUpdateException;
import ru.otus.library.repository.AuthorRepository;
import ru.otus.library.repository.BookRepository;
import ru.otus.library.repository.GenreRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = BookServiceImpl.class)
@DisplayName("Book service impl should:  ")
class BookServiceImplTest {
    @Autowired
    private BookServiceImpl bookService;
    @MockBean
    private BookRepository bookRepository;
    @MockBean
    private GenreRepository genreRepository;
    @MockBean
    private AuthorRepository authorRepository;
    @MockBean
    private PermissionService permissionService;
    @Captor
    private ArgumentCaptor<Book> bookCaptor;

    @DisplayName("call repository get all")
    @Test
    void shouldCallRepositoryGetAll() {
        bookService.getAll();
        verify(bookRepository).findAll();
    }

    @DisplayName("call repository get by id")
    @Test
    void shouldCallRepositoryGetById() {
        var id = 1L;
        var book = new Book();
        when(bookRepository.findById(id)).thenReturn(Optional.of(book));
        bookService.getById(id);
        verify(bookRepository).findById(id);
    }

    @DisplayName("throw EmptyResultException while getting empty book for getById")
    @Test
    void shouldThrowEmptyResultExceptionForEmptyResultFindById() {
        var id = 1;
        when(bookRepository.findById(id))
                .thenReturn(Optional.empty());
        assertThatThrownBy(() -> bookService.getById(id)).isInstanceOf(EmptyResultException.class);
    }

    @DisplayName("call dao delete by id")
    @Test
    void shouldCallRepositoryDeleteByIdBook() {
        var id = 1L;
        bookService.deleteById(id);
        verify(bookRepository).deleteExistingBookById(id);
        verify(permissionService, times(1)).removePermissionForAuthority(Book.class, id);
    }

    @WithMockUser(username = "user")
    @DisplayName("call dao saveOrUpdate book in insert method")
    @Test
    void shouldCallRepositorySaveBookInInsert() {
        var id = 1L;
        var bookName = "bookName";
        var author = new Author(id, "name");
        var genre = new Genre(id, "name");
        var book = new Book(bookName, author, genre);
        var bookWithId = new Book(id, bookName, author, genre);

        when(genreRepository.findById(id)).thenReturn(Optional.of(genre));
        when(authorRepository.findById(id)).thenReturn(Optional.of(author));
        when(bookRepository.save(bookCaptor.capture())).thenReturn(bookWithId);

        bookService.insert(book.getName(), book.getGenre().getId(), book.getAuthor().getId());

        verify(bookRepository).save(bookCaptor.capture());

        verify(permissionService, times(1)).addPermissionForAuthority(bookWithId, BasePermission.READ, "ROLE_USER");
        verify(permissionService, times(1)).addPermissionForAuthority(bookWithId, BasePermission.READ, "ROLE_ADMIN");

        assertThat(bookCaptor.getValue()).usingRecursiveComparison().isEqualTo(book);

    }

    @DisplayName("throw InvalidDataForUpdateException while add unexisting genre in insert method")
    @Test
    void shouldThrowInvalidDataForUpdateExceptionWithUnExistingGenreInInsert() {
        var id = 1L;
        when(genreRepository.findById(id)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> bookService.insert("name", id, id))
                .isInstanceOf(InvalidDataForUpdateException.class);
    }

    @DisplayName("throw InvalidDataForUpdateException while add unexisting genre in insert method")
    @Test
    void shouldThrowInvalidDataForUpdateExceptionWithUnExistingAuthorInInsert() {
        var id = 1L;
        when(authorRepository.findById(id)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> bookService.insert("name", id, id))
                .isInstanceOf(InvalidDataForUpdateException.class);
    }

    @DisplayName("call saveOrUpdate in update method")
    @Test
    void shouldCallSaveOrUpdateInUpdate() {
        var id = 1L;
        var bookName = "bookName";
        var author = new Author(id, "name");
        var genre = new Genre(id, "name");
        var expectedBook = new Book(id, bookName, author, genre);

        when(bookRepository.findById(id)).thenReturn(Optional.of(expectedBook));
        when(genreRepository.findById(id)).thenReturn(Optional.of(genre));
        when(authorRepository.findById(id)).thenReturn(Optional.of(author));

        bookService.update(id, expectedBook.getName(), expectedBook.getGenre().getId(), expectedBook.getAuthor().getId());

        verify(bookRepository).save(bookCaptor.capture());
        assertThat(bookCaptor.getValue()).usingRecursiveComparison().isEqualTo(expectedBook);

    }

    @DisplayName("call InvalidDataForUpdateException in update with unexisting Author")
    @Test
    void shouldCallInvalidDataForUpdateExceptionInUpdateWithUnExistingAuthor() {
        var id = 1L;
        when(authorRepository.findById(id)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> bookService.update(id, "name", id, id))
                .isInstanceOf(InvalidDataForUpdateException.class);

    }

    @DisplayName("call InvalidDataForUpdateException in update with unexisting Genre")
    @Test
    void shouldCallInvalidDataForUpdateExceptionInUpdateWithUnExistingGenre() {
        var id = 1L;
        when(genreRepository.findById(id)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> bookService.update(id, "name", id, id))
                .isInstanceOf(InvalidDataForUpdateException.class);

    }

    @DisplayName("call InvalidDataForUpdateException in update with unexisting Book")
    @Test
    void shouldCallInvalidDataForUpdateExceptionInUpdateWithUnExistingBook() {
        var id = 1L;
        when(bookRepository.findById(id)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> bookService.update(id, "name", id, id))
                .isInstanceOf(InvalidDataForUpdateException.class);

    }

}