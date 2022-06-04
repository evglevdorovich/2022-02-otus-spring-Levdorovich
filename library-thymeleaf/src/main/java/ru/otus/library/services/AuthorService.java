package ru.otus.library.services;

import ru.otus.library.domain.Author;
import ru.otus.library.domain.Book;

import java.util.List;

public interface AuthorService {
    List<Author> getAll();

    List<Author> getAllExceptBooksAuthor(Book book);
}
