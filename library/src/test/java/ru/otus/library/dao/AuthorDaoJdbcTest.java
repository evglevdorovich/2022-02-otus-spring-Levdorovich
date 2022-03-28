package ru.otus.library.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.otus.library.domain.Author;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@DisplayName("Author dao JDBC should: ")
@Import(AuthorDaoJdbc.class)
class AuthorDaoJdbcTest {
    private static final long EXISTING_FIRST_AUTHOR_ID = 1;
    private static final String EXISTING_FIRST_AUTHOR_NAME = "Harper Lee";
    private static final long EXISTING_SECOND_AUTHOR_ID = 2;
    private static final String EXISTING_SECOND_AUTHOR_NAME = "Velma Walls";
    @Autowired
    private AuthorDaoJdbc authorDao;

    @Test
    @DisplayName("return expected authors list")
    void shouldReturnExpectedAuthorsList() {
        var expectedAuthors = List.of(new Author(EXISTING_FIRST_AUTHOR_ID
                , EXISTING_FIRST_AUTHOR_NAME), (new Author(EXISTING_SECOND_AUTHOR_ID
                , EXISTING_SECOND_AUTHOR_NAME)));
        var actualAuthors = authorDao.getAll();
        assertThat(expectedAuthors).usingRecursiveFieldByFieldElementComparator().isEqualTo(actualAuthors);
    }

}