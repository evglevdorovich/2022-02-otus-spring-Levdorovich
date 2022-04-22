package ru.otus.library.converter;

import org.springframework.stereotype.Component;
import ru.otus.library.model.Author;

import java.util.List;

@Component
public class AuthorViewConverterImpl implements AuthorViewConverter {

    @Override
    public String getViewAuthors(List<Author> authors) {
        var sb = new StringBuilder();
        for (var author : authors) {
            sb.append(author.getName()).append("\n");
        }
        return sb.toString();
    }
}
