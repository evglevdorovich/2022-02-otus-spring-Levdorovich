package ru.otus.library.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import ru.otus.library.domain.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@DisplayName("Genre dao Jdbc Test should:")
@Import(GenreRepositoryJpa.class)
class GenreRepositoryJpaTest {
    private static final long EXISTING_FIRST_GENRE_ID = 1;
    private static final String EXISTING_FIRST_GENRE_NAME = "myth";
    private static final long EXISTING_SECOND_GENRE_ID = 2;
    private static final String EXISTING_SECOND_GENRE_NAME = "romance";

    @Autowired
    private GenreRepositoryJpa genreRepositoryJpa;

    @Test
    @DisplayName("return expected genres list")
    void shouldReturnExpectedGenresList() {
        var expectedGenres = List.of(new Genre(EXISTING_FIRST_GENRE_ID
                , EXISTING_FIRST_GENRE_NAME), (new Genre(EXISTING_SECOND_GENRE_ID
                , EXISTING_SECOND_GENRE_NAME)));
        var actualGenres = genreRepositoryJpa.getAll();
        assertThat(actualGenres).usingRecursiveFieldByFieldElementComparator().isEqualTo(expectedGenres);
    }

    @Test
    @DisplayName("return expected genre")
    void shouldReturnExpectedGenre() {
        var expectedGenre = new Genre(EXISTING_SECOND_GENRE_ID, EXISTING_SECOND_GENRE_NAME);
        var optionalOfActualGenre = genreRepositoryJpa.getById(EXISTING_SECOND_GENRE_ID);
        assertThat(optionalOfActualGenre).isNotEmpty().get().usingRecursiveComparison().isEqualTo(expectedGenre);
    }
}