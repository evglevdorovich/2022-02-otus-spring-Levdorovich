package ru.otus.library.services;

import org.springframework.transaction.annotation.Transactional;
import ru.otus.library.domain.Book;

import java.util.List;

public interface BookService {
    @Transactional
    List<Book> getAll();

    @Transactional
    void update(Book book);

    @Transactional
    void insert(Book book);

    @Transactional
    void deleteById(long id);

    @Transactional
    Book getById(long id);
}
