package ru.otus.library.converter;

import ru.otus.library.domain.Author;

import java.util.List;

public interface AuthorViewConverter {
    String getViewAuthors(List<Author> authors);
}
