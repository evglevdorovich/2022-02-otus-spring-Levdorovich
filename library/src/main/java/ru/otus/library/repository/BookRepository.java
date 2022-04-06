package ru.otus.library.repository;

import ru.otus.library.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository {
    List<Book> getAll();

    Optional<Book> getById(long id);

    Book saveOrUpdate(Book book);

    void deleteById(long id);
}
