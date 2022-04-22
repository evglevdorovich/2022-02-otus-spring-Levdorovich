package ru.otus.library.repository;

import ru.otus.library.model.Book;

public interface BookRepositoryCustom {
    Book update(Book book);
    boolean deleteBookById(String id);
}
