package ru.otus.library.converter;

import org.springframework.stereotype.Component;
import ru.otus.library.domain.Genre;

import java.util.List;

@Component
public class GenreViewConverterImpl implements GenreViewConverter {
    private final static String ID = "id";

    @Override
    public String getViewGenres(List<Genre> genres) {
        var sb = new StringBuilder();
        for (var genre : genres) {
            sb.append(ID)
                    .append(":")
                    .append(genre.getId())
                    .append(" - ")
                    .append(genre.getName())
                    .append("\n");
        }
        return sb.toString();
    }
}
