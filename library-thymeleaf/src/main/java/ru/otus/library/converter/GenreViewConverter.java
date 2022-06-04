package ru.otus.library.converter;

import ru.otus.library.domain.Genre;

import java.util.List;

public interface GenreViewConverter {
    String getViewGenres(List<Genre> genres);
}
