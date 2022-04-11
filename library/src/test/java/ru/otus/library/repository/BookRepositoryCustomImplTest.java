package ru.otus.library.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.DirtiesContext;
import ru.otus.library.domain.Book;
import ru.otus.library.exceptions.InvalidDataForUpdateException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@DisplayName("Book Repository DAO Test should:")
class BookRepositoryCustomImplTest {

    private static final long FIRST_BOOK_ID = 1;
    private static final long UN_EXISTING_BOOK_ID = 3;

    @Autowired
    private BookRepositoryCustomImpl bookRepositoryCustom;
    @Autowired
    private TestEntityManager entityManager;

    @DisplayName("correct delete book for id")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    @Test
    void shouldCorrectDeleteBookById() {
        var book = entityManager.find(Book.class, FIRST_BOOK_ID);
        bookRepositoryCustom.deleteExistingBookById(FIRST_BOOK_ID);
        assertThat(entityManager.find(Book.class, book.getId())).isNull();
    }

    @DisplayName("throw an exception while deleting book with unexisting id")
    @Test
    void shouldThrowInvalidDataExceptionWhileDeletingUnExistingBook() {
        assertThatThrownBy(() -> bookRepositoryCustom.deleteExistingBookById(UN_EXISTING_BOOK_ID))
                .isInstanceOf(InvalidDataForUpdateException.class);
    }
}