package ru.otus.library.services;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
    @Captor
    private ArgumentCaptor<Book> bookCaptor;

    @DisplayName("call dao get all")
    @Test
    void shouldCallDaoGetAll() {
        bookService.getAll();
        verify(bookRepository).getAll();
    }

    @DisplayName("call dao get by id")
    @Test
    void shouldCallDaoGetById() {
        var id = 1L;
        var book = new Book();
        when(bookRepository.getById(id)).thenReturn(Optional.of(book));
        bookService.getById(id);
        verify(bookRepository).getById(id);
    }

    @DisplayName("throw EmptyResultException while getting empty book for getById")
    @Test
    void shouldThrowEmptyResultExceptionForEmptyResultGetById() {
        var id = 1;
        when(bookRepository.getById(id))
                .thenReturn(Optional.empty());
        assertThatThrownBy(() -> bookService.getById(id)).isInstanceOf(EmptyResultException.class);
    }

    @DisplayName("call dao delete by id")
    @Test
    void shouldCallDaoDeleteByIdBook() {
        var id = 1;
        bookService.deleteById(id);
        verify(bookRepository).deleteById(id);
    }

    @DisplayName("call dao saveOrUpdate book in insert method")
    @Test
    void shouldCallDaoSaveOrUpdateBookInInsert() {
        var id = 1L;
        var bookName = "bookName";
        var author = new Author(id, "name");
        var genre = new Genre(id, "name");
        var book = new Book(bookName, author, genre);

        when(genreRepository.getById(id)).thenReturn(Optional.of(genre));
        when(authorRepository.getById(id)).thenReturn(Optional.of(author));

        bookService.insert(book.getName(), book.getGenre().getId(), book.getAuthor().getId());

        verify(bookRepository).saveOrUpdate(bookCaptor.capture());

        assertThat(bookCaptor.getValue()).usingRecursiveComparison().isEqualTo(book);
    }

    @DisplayName("throw InvalidDataForUpdateException while add unexisting genre in insert method")
    @Test
    void shouldThrowInvalidDataForUpdateExceptionWithUnExistingGenreInInsert() {
        var id = 1L;
        when(genreRepository.getById(id)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> bookService.insert("name", id, id))
                .isInstanceOf(InvalidDataForUpdateException.class);
    }

    @DisplayName("throw InvalidDataForUpdateException while add unexisting genre in insert method")
    @Test
    void shouldThrowInvalidDataForUpdateExceptionWithUnExistingAuthorInInsert() {
        var id = 1L;
        when(authorRepository.getById(id)).thenReturn(Optional.empty());
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

        when(bookRepository.getById(id)).thenReturn(Optional.of(expectedBook));
        when(genreRepository.getById(id)).thenReturn(Optional.of(genre));
        when(authorRepository.getById(id)).thenReturn(Optional.of(author));

        bookService.update(id, expectedBook.getName(), expectedBook.getGenre().getId(), expectedBook.getAuthor().getId());

        verify(bookRepository).saveOrUpdate(bookCaptor.capture());
        assertThat(bookCaptor.getValue()).usingRecursiveComparison().isEqualTo(expectedBook);

    }

    @DisplayName("call InvalidDataForUpdateException in update with unexisting Author")
    @Test
    void shouldCallInvalidDataForUpdateExceptionInUpdateWithUnExistingAuthor() {
        var id = 1L;
        when(authorRepository.getById(id)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> bookService.update(id, "name", id, id))
                .isInstanceOf(InvalidDataForUpdateException.class);

    }

    @DisplayName("call InvalidDataForUpdateException in update with unexisting Genre")
    @Test
    void shouldCallInvalidDataForUpdateExceptionInUpdateWithUnExistingGenre() {
        var id = 1L;
        when(genreRepository.getById(id)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> bookService.update(id, "name", id, id))
                .isInstanceOf(InvalidDataForUpdateException.class);

    }

    @DisplayName("call InvalidDataForUpdateException in update with unexisting Book")
    @Test
    void shouldCallInvalidDataForUpdateExceptionInUpdateWithUnExistingBook() {
        var id = 1L;
        when(bookRepository.getById(id)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> bookService.update(id, "name", id, id))
                .isInstanceOf(InvalidDataForUpdateException.class);

    }

}