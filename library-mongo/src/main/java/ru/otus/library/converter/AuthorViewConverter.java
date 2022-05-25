package ru.otus.library.converter;

import ru.otus.library.model.Author;

import java.util.List;

public interface AuthorViewConverter {
    String getViewAuthors(List<Author> authors);
}
