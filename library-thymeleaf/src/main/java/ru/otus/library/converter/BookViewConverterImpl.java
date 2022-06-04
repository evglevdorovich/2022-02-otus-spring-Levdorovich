package ru.otus.library.converter;

import org.springframework.stereotype.Component;
import ru.otus.library.domain.Book;

import java.util.List;

@Component
public class BookViewConverterImpl implements BookViewConverter {
    private final static String AUTHOR_ID = "author id";
    private final static String GENRE_ID = "genre id";
    private final static String ID = "id";
    private final static String GENRE_NAME = "genre name";
    private final static String AUTHOR_NAME = "author name";

    @Override
    public String getViewBooks(List<Book> books) {
        var sb = new StringBuilder();
        for (var book : books) {
            addViewOfBookToStringBuilder(sb, book);
        }
        return sb.toString();
    }

    @Override
    public String getViewBook(Book book) {
        var sb = new StringBuilder();
        addViewOfBookToStringBuilder(sb, book);
        return sb.toString();
    }

    private void addViewOfBookToStringBuilder(StringBuilder sb, Book book) {
        sb.append(ID)
                .append(":")
                .append(book.getId())
                .append(" - ")
                .append(book.getName())
                .append(", ")
                .append(AUTHOR_ID)
                .append(" - ")
                .append(book.getAuthor().getId())
                .append(", ")
                .append(AUTHOR_NAME)
                .append(" - ")
                .append(book.getAuthor().getName())
                .append(", ")
                .append(GENRE_ID)
                .append(" - ")
                .append(book.getGenre().getId())
                .append(", ")
                .append(GENRE_NAME)
                .append(" - ")
                .append(book.getGenre().getName())
                .append("\n");
    }
}
