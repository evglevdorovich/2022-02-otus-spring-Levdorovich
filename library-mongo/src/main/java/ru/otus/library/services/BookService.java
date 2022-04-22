package ru.otus.library.services;

import ru.otus.library.model.Book;

import java.util.List;

public interface BookService {
    List<Book> getAll();

    void update(String id, String bookName, String genreName, String authorName);

    void insert(String name, String genreName, String authorName);

    void deleteById(String id);

    Book getById(String id);
}
