package ru.otus.library.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.otus.library.domain.Author;
import ru.otus.library.domain.Book;
import ru.otus.library.domain.Genre;
import ru.otus.library.exceptions.EmptyResultException;
import ru.otus.library.exceptions.InvalidDataForUpdateException;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@JdbcTest
@DisplayName("Book dao Jdbc Test should:")
@Import(BookDaoJdbc.class)
class BookDaoJdbcTest {

    private static final int EXISTING_FIRST_BOOK_ID = 1;
    private static final String EXISTING_FIRST_BOOK_NAME = "December 1941";
    private static final long EXISTING_FIRST_BOOK_GENRE_ID = 1;
    private static final String EXISTING_FIRST_BOOK_GENRE_NAME = "myth";
    private static final long EXISTING_FIRST_BOOK_AUTHOR_ID = 1;
    private static final String EXISTING_FIRST_BOOK_AUTHOR_NAME = "Harper Lee";
    private static final long EXISTING_SECOND_BOOK_ID = 2;
    private static final String EXISTING_SECOND_BOOK_NAME = "The Inevitable";
    private static final long EXISTING_SECOND_BOOK_GENRE_ID = 2;
    private static final String EXISTING_SECOND_BOOK_GENRE_NAME = "romance";
    private static final long EXISTING_SECOND_BOOK_AUTHOR_ID = 2;
    private static final String EXISTING_SECOND_BOOK_AUTHOR_NAME = "Velma Walls";
    private static final long UN_EXISTING_BOOK_ID = 3;
    private static final long UN_EXISTING_GENRE_ID = 3;


    @Autowired
    private BookDaoJdbc bookDaoJdbc;

    @DisplayName("should return expected book list")
    @Test
    void shouldReturnExpectedBookList() {
        var expectedBooks = List.of(new Book(EXISTING_FIRST_BOOK_ID, EXISTING_FIRST_BOOK_NAME,
                        new Author(EXISTING_FIRST_BOOK_AUTHOR_ID, EXISTING_FIRST_BOOK_AUTHOR_NAME),
                        new Genre(EXISTING_FIRST_BOOK_GENRE_ID, EXISTING_FIRST_BOOK_GENRE_NAME)),
                new Book(EXISTING_SECOND_BOOK_ID
                        , EXISTING_SECOND_BOOK_NAME,
                        new Author(EXISTING_SECOND_BOOK_AUTHOR_ID, EXISTING_SECOND_BOOK_AUTHOR_NAME),
                        new Genre(EXISTING_SECOND_BOOK_GENRE_ID, EXISTING_SECOND_BOOK_GENRE_NAME)));

        var actualBooks = bookDaoJdbc.getAll();
        assertThat(expectedBooks).usingRecursiveFieldByFieldElementComparator().isEqualTo(actualBooks);
    }

    @DisplayName("correct delete book for id")
    @Test
    void shouldCorrectDeleteBookById() {
        assertThatCode(() -> bookDaoJdbc.getById(EXISTING_FIRST_BOOK_ID))
                .doesNotThrowAnyException();

        bookDaoJdbc.deleteById(EXISTING_FIRST_BOOK_ID);

        assertThatThrownBy(() -> bookDaoJdbc.getById(EXISTING_FIRST_BOOK_ID))
                .isInstanceOf(EmptyResultException.class);
    }

    @DisplayName("should return expected book by id")
    @Test
    void shouldReturnExpectedBookById() {
        var expectedBook = new Book(EXISTING_FIRST_BOOK_ID, EXISTING_FIRST_BOOK_NAME,
                new Author(EXISTING_FIRST_BOOK_AUTHOR_ID, EXISTING_FIRST_BOOK_AUTHOR_NAME),
                new Genre(EXISTING_FIRST_BOOK_GENRE_ID, EXISTING_FIRST_BOOK_GENRE_NAME));
        var actualPerson = bookDaoJdbc.getById(expectedBook.getId());
        assertThat(actualPerson).usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @DisplayName("throw EmptyResultException getting book by unexisting id")
    @Test
    void shouldThrowEmptyResultExceptionGettingBookByUnExistingId() {
        assertThatThrownBy(() -> bookDaoJdbc.getById(UN_EXISTING_BOOK_ID))
                .isInstanceOf(EmptyResultException.class);
    }

    @DisplayName("insert book")
    @Test
    void shouldInsertBook() {
        var expectedBook = new Book(3, "Igor",
                new Author(EXISTING_FIRST_BOOK_AUTHOR_ID, EXISTING_FIRST_BOOK_AUTHOR_NAME),
                new Genre(EXISTING_FIRST_BOOK_GENRE_ID, EXISTING_FIRST_BOOK_GENRE_NAME));
        bookDaoJdbc.insert(expectedBook);
        var actualBook = bookDaoJdbc.getById(expectedBook.getId());
        assertThat(actualBook).usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @DisplayName("throw InvalidDataForUpdateException while inserting book with unexisting genres")
    @Test
    void shouldThrowUpdateExceptionInsertingWhileInsertingWithUnExistingGenres() {
        var book = new Book(2, "Igor", new Author(EXISTING_FIRST_BOOK_AUTHOR_ID, EXISTING_FIRST_BOOK_AUTHOR_NAME),
                new Genre(UN_EXISTING_GENRE_ID, EXISTING_FIRST_BOOK_GENRE_NAME));
        assertThatThrownBy(() -> bookDaoJdbc.insert(book)).isInstanceOf(InvalidDataForUpdateException.class);
    }

    @DisplayName("update book")
    @Test
    void shouldUpdateBook() {
        var expectedBook = new Book(EXISTING_FIRST_BOOK_ID, "some",
                new Author(EXISTING_SECOND_BOOK_AUTHOR_ID, EXISTING_SECOND_BOOK_AUTHOR_NAME),
                new Genre(EXISTING_SECOND_BOOK_GENRE_ID, EXISTING_SECOND_BOOK_GENRE_NAME));
        bookDaoJdbc.update(expectedBook);
        var actualBook = bookDaoJdbc.getById(expectedBook.getId());
        assertThat(actualBook).usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @DisplayName("throw InvalidDataForUpdateException while updating book with unexisting genres")
    @Test
    void shouldThrowUpdateExceptionInsertingWhileUpdatingWithUnExistingGenres() {
        var book = new Book(2, "Igor",
                new Author(EXISTING_FIRST_BOOK_AUTHOR_ID, EXISTING_FIRST_BOOK_AUTHOR_NAME),
                new Genre(UN_EXISTING_GENRE_ID, EXISTING_FIRST_BOOK_GENRE_NAME));
        assertThatThrownBy(() -> bookDaoJdbc.update(book)).isInstanceOf(InvalidDataForUpdateException.class);
    }

}