package ru.otus.library.services;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.library.model.Author;
import ru.otus.library.model.Book;
import ru.otus.library.model.Genre;
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

    @DisplayName("call repository get all")
    @Test
    void shouldCallRepositoryGetAll() {
        bookService.getAll();
        verify(bookRepository).findAll();
    }

    @DisplayName("call repository get by id")
    @Test
    void shouldCallRepositoryGetById() {
        var id = "some id";
        var book = new Book();
        when(bookRepository.findById(id)).thenReturn(Optional.of(book));
        bookService.getById(id);
        verify(bookRepository).findById(id);
    }

    @DisplayName("throw EmptyResultException while getting empty book for getById")
    @Test
    void shouldThrowEmptyResultExceptionForEmptyResultFindById() {
        var id = "some id";
        when(bookRepository.findById(id))
                .thenReturn(Optional.empty());
        assertThatThrownBy(() -> bookService.getById(id)).isInstanceOf(EmptyResultException.class);
    }

    @DisplayName("call dao delete by id")
    @Test
    void shouldCallRepositoryDeleteByIdBook() {
        var id = "book id";
        when(bookRepository.deleteBookById(id))
                .thenReturn(true);
        bookService.deleteById(id);
        verify(bookRepository).deleteBookById(id);
    }

    @DisplayName("call dao delete by id throw invalid data exception for false")
    @Test
    void shouldForFalseDeleteThrowInvalidDataException() {
        var id = "book id";
        when(bookRepository.deleteBookById(id))
                .thenReturn(false);
        assertThatThrownBy(() -> bookService.deleteById(id)).isInstanceOf(InvalidDataForUpdateException.class);
    }

    @DisplayName("call dao saveOrUpdate book in insert method")
    @Test
    void shouldCallRepositorySaveBookInInsert() {
        var id = "1L";
        var bookName = "bookName";
        var genreName = "genreName";
        var authorName = "authorName";
        var author = new Author(id, authorName);
        var genre = new Genre(id, genreName);
        var book = new Book(bookName, author, genre);

        when(genreRepository.findByName(genreName)).thenReturn(Optional.of(genre));
        when(authorRepository.findByName(authorName)).thenReturn(Optional.of(author));

        bookService.insert(book.getName(), book.getGenre().getName(), book.getAuthor().getName());

        verify(bookRepository).save(bookCaptor.capture());

        assertThat(bookCaptor.getValue()).usingRecursiveComparison().isEqualTo(book);
    }

    @DisplayName("throw InvalidDataForUpdateException while add unexisting genre in insert method")
    @Test
    void shouldThrowInvalidDataForUpdateExceptionWithUnExistingGenreInInsert() {
        var genreName = "name";
        when(genreRepository.findById(genreName)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> bookService.insert("name", genreName, genreName))
                .isInstanceOf(InvalidDataForUpdateException.class);
    }

    @DisplayName("throw InvalidDataForUpdateException while add unexisting genre in insert method")
    @Test
    void shouldThrowInvalidDataForUpdateExceptionWithUnExistingAuthorInInsert() {
        var name = "name";
        when(authorRepository.findById(name)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> bookService.insert("name", name, name))
                .isInstanceOf(InvalidDataForUpdateException.class);
    }

    @DisplayName("call saveOrUpdate in update method")
    @Test
    void shouldCallSaveOrUpdateInUpdate() {
        var id = "1L";
        var bookName = "bookName";
        var genreName = "genreName";
        var authorName = "authorName";
        var author = new Author(id, authorName);
        var genre = new Genre(id, genreName);
        var expectedBook = new Book(id, bookName, author, genre);

        when(bookRepository.findById(id)).thenReturn(Optional.of(expectedBook));
        when(genreRepository.findByName(genreName)).thenReturn(Optional.of(genre));
        when(authorRepository.findByName(authorName)).thenReturn(Optional.of(author));

        bookService.update(id, expectedBook.getName(), expectedBook.getGenre().getName(), expectedBook.getAuthor().getName());

        verify(bookRepository).update(bookCaptor.capture());
        assertThat(bookCaptor.getValue()).usingRecursiveComparison().isEqualTo(expectedBook);

    }

    @DisplayName("call InvalidDataForUpdateException in update with unexisting Author")
    @Test
    void shouldCallInvalidDataForUpdateExceptionInUpdateWithUnExistingAuthor() {
        var name = "name";
        var id = "1L";
        when(authorRepository.findByName(name)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> bookService.update(id, "name", name, name))
                .isInstanceOf(InvalidDataForUpdateException.class);

    }

    @DisplayName("call InvalidDataForUpdateException in update with unexisting Genre")
    @Test
    void shouldCallInvalidDataForUpdateExceptionInUpdateWithUnExistingGenre() {
        var name = "name";
        var id = "1L";
        when(genreRepository.findByName(name)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> bookService.update(id, "name", name, name))
                .isInstanceOf(InvalidDataForUpdateException.class);

    }

    @DisplayName("call InvalidDataForUpdateException in update with unexisting Book")
    @Test
    void shouldCallInvalidDataForUpdateExceptionInUpdateWithUnExistingBook() {
        var id = "1L";
        var name = "name";
        when(bookRepository.findById(id)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> bookService.update(id, "name", name, name))
                .isInstanceOf(InvalidDataForUpdateException.class);

    }

}