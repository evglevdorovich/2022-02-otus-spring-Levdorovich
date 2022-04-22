package ru.otus.library.converter;

import org.springframework.stereotype.Component;
import ru.otus.library.model.Genre;

import java.util.List;

@Component
public class GenreViewConverterImpl implements GenreViewConverter {

    @Override
    public String getViewGenres(List<Genre> genres) {
        var sb = new StringBuilder();
        for (var genre : genres) {
            sb.append(genre.getName()).append("\n");
        }
        return sb.toString();
    }
}
