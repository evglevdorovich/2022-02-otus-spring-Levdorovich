package ru.otus.library.repository;

import ru.otus.library.domain.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreRepository {
    List<Genre> getAll();

    Optional<Genre> getById(long id);
}
