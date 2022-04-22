package ru.otus.library.services;

import ru.otus.library.model.Genre;

import java.util.List;

public interface GenreService {
    List<Genre> findAll();
}
