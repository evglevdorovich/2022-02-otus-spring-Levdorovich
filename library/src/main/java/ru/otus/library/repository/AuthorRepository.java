package ru.otus.library.repository;

import ru.otus.library.domain.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository {
    List<Author> getAll();

    Optional<Author> getById(long id);
}
