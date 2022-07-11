package ru.otus.library_rest.projections;

import org.springframework.data.rest.core.config.Projection;
import ru.otus.library_rest.domain.Author;
import ru.otus.library_rest.domain.Book;
import ru.otus.library_rest.domain.Genre;

@Projection(name = "bookWithAuthorAndGenre", types = {Book.class})
public interface BookWithAuthorAndGenreProjection {
    String getName();

    Author getAuthor();

    Genre getGenre();
}