package ru.otus.library.converter;

import org.springframework.stereotype.Component;
import ru.otus.library.domain.Author;

import java.util.List;

@Component
public class AuthorViewConverterImpl implements AuthorViewConverter {
    private static final String ID = "id";

    @Override
    public String getViewAuthors(List<Author> authors) {
        var sb = new StringBuilder();
        for (var author : authors) {
            sb.append(ID)
                    .append(":")
                    .append(author.getId())
                    .append(" - ")
                    .append(author.getName())
                    .append("\n");
        }
        return sb.toString();
    }
}
