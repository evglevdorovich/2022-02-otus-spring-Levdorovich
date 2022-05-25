package ru.otus.library.repository;

import ru.otus.library.model.Book;

import java.util.Optional;

public interface BookRepositoryCustom {
    Book update(Book book);

    boolean deleteBookById(String id);

    Optional<Book> findPartialBookById(String id);
}
