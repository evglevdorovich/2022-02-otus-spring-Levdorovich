package ru.otus.library.dao;

import ru.otus.library.domain.Author;

import java.util.List;

public interface AuthorDao {
    List<Author> getAll();
}
