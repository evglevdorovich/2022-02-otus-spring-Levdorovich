package ru.otus.library.repository;

import org.hibernate.Hibernate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import ru.otus.library.domain.Author;
import ru.otus.library.domain.Book;
import ru.otus.library.domain.Genre;
import ru.otus.library.exceptions.InvalidDataForUpdateException;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJpaTest
@DisplayName("Book dao Jdbc Test should:")
@Import(BookRepositoryJpa.class)
class BookRepositoryJPATest {

    private static final long FIRST_BOOK_ID = 1;
    private static final String FIRST_BOOK_NAME = "December 1941";
    private static final long FIRST_BOOK_GENRE_ID = 1;
    private static final String FIRST_BOOK_GENRE_NAME = "myth";
    private static final long FIRST_BOOK_AUTHOR_ID = 1;
    private static final String FIRST_BOOK_AUTHOR_NAME = "Harper Lee";
    private static final long SECOND_BOOK_ID = 2;
    private static final String SECOND_BOOK_NAME = "The Inevitable";
    private static final long SECOND_BOOK_GENRE_ID = 2;
    private static final String SECOND_BOOK_GENRE_NAME = "romance";
    private static final long SECOND_BOOK_AUTHOR_ID = 2;
    private static final String SECOND_BOOK_AUTHOR_NAME = "Velma Walls";
    public static final String THIRD_BOOK_NAME = "Igor";
    private static final long UN_EXISTING_BOOK_ID = 3;


    @Autowired
    private BookRepositoryJpa bookRepositoryJpa;
    @Autowired
    private TestEntityManager entityManager;

    @DisplayName("should return expected book list")
    @Test
    void shouldReturnExpectedBookList() {
        var expectedBooks = List.of(new Book(FIRST_BOOK_ID, FIRST_BOOK_NAME,
                        new Author(FIRST_BOOK_AUTHOR_ID, FIRST_BOOK_AUTHOR_NAME),
                        new Genre(FIRST_BOOK_GENRE_ID, FIRST_BOOK_GENRE_NAME)),
                new Book(SECOND_BOOK_ID
                        , SECOND_BOOK_NAME,
                        new Author(SECOND_BOOK_AUTHOR_ID, SECOND_BOOK_AUTHOR_NAME),
                        new Genre(SECOND_BOOK_GENRE_ID, SECOND_BOOK_GENRE_NAME)));

        var actualBooks = bookRepositoryJpa.getAll();
        assertThat(expectedBooks).usingRecursiveFieldByFieldElementComparator().isEqualTo(actualBooks);
    }

    @DisplayName("correct delete book for id")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    @Test
    void shouldCorrectDeleteBookById() {
        var book = entityManager.find(Book.class, FIRST_BOOK_ID);
        bookRepositoryJpa.deleteById(FIRST_BOOK_ID);
        assertThat(entityManager.find(Book.class, book.getId())).isNull();
    }

    @DisplayName("throw an exception while deleting book with unexisting id")
    @Test
    void shouldThrowInvalidDataExceptionWhileDeletingUnExistingBook() {
        assertThatThrownBy(() -> bookRepositoryJpa.deleteById(UN_EXISTING_BOOK_ID))
                .isInstanceOf(InvalidDataForUpdateException.class);
    }

    @DisplayName("should return expected book by id")
    @Test
    void shouldReturnExpectedBookById() {
        var expectedBook = new Book(FIRST_BOOK_ID, FIRST_BOOK_NAME,
                new Author(FIRST_BOOK_AUTHOR_ID, FIRST_BOOK_AUTHOR_NAME),
                new Genre(FIRST_BOOK_GENRE_ID, FIRST_BOOK_GENRE_NAME));
        var optionalOfActualPerson = bookRepositoryJpa.getById(expectedBook.getId());
        assertThat(optionalOfActualPerson).isNotEmpty().get().usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @DisplayName("save book")
    @Test
    void shouldInsertBook() {
        var expectedBook = new Book(UN_EXISTING_BOOK_ID, THIRD_BOOK_NAME,
                new Author(FIRST_BOOK_AUTHOR_ID, FIRST_BOOK_AUTHOR_NAME),
                new Genre(FIRST_BOOK_GENRE_ID, FIRST_BOOK_GENRE_NAME));
        bookRepositoryJpa.saveOrUpdate(expectedBook);

        var actualBook = entityManager.find(Book.class, expectedBook.getId());
        var unProxiedGenre = Hibernate.unproxy(actualBook.getGenre(), Genre.class);
        var unProxiedAuthor = Hibernate.unproxy(actualBook.getAuthor(), Author.class);
        actualBook.setGenre(unProxiedGenre);
        actualBook.setAuthor(unProxiedAuthor);

        assertThat(actualBook).usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @DisplayName("correct update book")
    @Test
    void shouldUpdateBook() {
        var expectedBook = new Book(FIRST_BOOK_ID, "some",
                new Author(SECOND_BOOK_AUTHOR_ID, SECOND_BOOK_AUTHOR_NAME),
                new Genre(SECOND_BOOK_GENRE_ID, SECOND_BOOK_GENRE_NAME));

        bookRepositoryJpa.saveOrUpdate(expectedBook);
        var actualBook = entityManager.find(Book.class, expectedBook.getId());
        var unProxiedGenre = Hibernate.unproxy(actualBook.getGenre(), Genre.class);
        var unProxiedAuthor = Hibernate.unproxy(actualBook.getAuthor(), Author.class);
        actualBook.setGenre(unProxiedGenre);
        actualBook.setAuthor(unProxiedAuthor);
        assertThat(actualBook).usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @DisplayName("correct save book")
    @Test
    void shouldSaveBook() {
        var expectedBook = new Book(UN_EXISTING_BOOK_ID, "changed",
                new Author(SECOND_BOOK_AUTHOR_ID, SECOND_BOOK_AUTHOR_NAME),
                new Genre(SECOND_BOOK_GENRE_ID, SECOND_BOOK_GENRE_NAME));

        bookRepositoryJpa.saveOrUpdate(expectedBook);
        bookRepositoryJpa.getAll();
        var actualBook = entityManager.find(Book.class, expectedBook.getId());
        var unProxiedGenre = Hibernate.unproxy(actualBook.getGenre(), Genre.class);
        var unProxiedAuthor = Hibernate.unproxy(actualBook.getAuthor(), Author.class);
        actualBook.setGenre(unProxiedGenre);
        actualBook.setAuthor(unProxiedAuthor);

        assertThat(actualBook).usingRecursiveComparison().isEqualTo(expectedBook);
    }

}