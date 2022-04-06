package ru.otus.library.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import ru.otus.library.domain.Author;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Author dao JDBC should: ")
@DataJpaTest
@Import(AuthorRepositoryJpa.class)
class AuthorRepositoryJpaTest {
    private static final long EXISTING_FIRST_AUTHOR_ID = 1;
    private static final String EXISTING_FIRST_AUTHOR_NAME = "Harper Lee";
    private static final long EXISTING_SECOND_AUTHOR_ID = 2;
    private static final String EXISTING_SECOND_AUTHOR_NAME = "Velma Walls";
    @Autowired
    private AuthorRepositoryJpa authorDao;

    @Test
    @DisplayName("return expected authors list")
    void shouldReturnExpectedAuthorsList() {
        var author1 = new Author(EXISTING_FIRST_AUTHOR_ID, EXISTING_FIRST_AUTHOR_NAME);
        var author2 = new Author(EXISTING_SECOND_AUTHOR_ID, EXISTING_SECOND_AUTHOR_NAME);
        var expectedAuthors = List.of(author1, author2);
        var actualAuthors = authorDao.getAll();
        assertThat(actualAuthors).usingRecursiveFieldByFieldElementComparator().isEqualTo(expectedAuthors);
    }

    @Test
    @DisplayName("return expected author")
    void shouldReturnExpectedOptionalWithExistingAuthor() {
        var expectedAuthor = new Author(EXISTING_SECOND_AUTHOR_ID, EXISTING_SECOND_AUTHOR_NAME);
        var optionalOfActualAuthor = authorDao.getById(EXISTING_SECOND_AUTHOR_ID);
        assertThat(optionalOfActualAuthor).isNotEmpty().get().usingRecursiveComparison().isEqualTo(expectedAuthor);
    }

}