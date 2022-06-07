package ru.otus.library.converter;

import ru.otus.library.domain.Book;

import java.util.List;

public interface BookViewConverter {
    String getViewBooks(List<Book> books);

    String getViewBook(Book book);
}
