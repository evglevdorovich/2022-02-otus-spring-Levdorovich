package ru.otus.library.dao;

import ru.otus.library.domain.Book;

import java.util.List;

public interface BookDao {
    List<Book> getAll();

    Book getById(long id);

    void insert(Book book);

    void update(Book book);

    void deleteById(long id);
}
