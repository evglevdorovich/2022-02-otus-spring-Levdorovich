package ru.otus.library.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.otus.library.domain.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@DisplayName("Genre dao Jdbc Test should:")
@Import(GenreDaoJdbc.class)
class GenreDaoJdbcTest {
    private static final long EXISTING_FIRST_GENRE_ID = 1;
    private static final String EXISTING_FIRST_GENRE_NAME = "myth";
    private static final long EXISTING_SECOND_GENRE_ID = 2;
    private static final String EXISTING_SECOND_GENRE_NAME = "romance";

    @Autowired
    private GenreDaoJdbc genreDaoJdbc;

    @Test
    @DisplayName("return expected genres list")
    void shouldReturnExpectedAuthorsList() {
        var expectedGenres = List.of(new Genre(EXISTING_FIRST_GENRE_ID
                , EXISTING_FIRST_GENRE_NAME), (new Genre(EXISTING_SECOND_GENRE_ID
                , EXISTING_SECOND_GENRE_NAME)));
        var actualGenres = genreDaoJdbc.getAll();
        assertThat(expectedGenres).usingRecursiveFieldByFieldElementComparator().isEqualTo(actualGenres);
    }
}