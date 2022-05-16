package ru.otus.library.services;

import ru.otus.library.domain.Book;

import java.util.List;

public interface BookService {
    List<Book> getAll();

    void update(long id, String bookName, long genreId, long authorId);

    void insert(String name, long genreId, long authorId);

    void deleteById(long id);

    Book getById(long id);

}
