package ru.otus.library.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.library.converter.AuthorViewConverter;
import ru.otus.library.converter.BookViewConverter;
import ru.otus.library.converter.GenreViewConverter;
import ru.otus.library.exceptions.CannotUpdateException;
import ru.otus.library.exceptions.EmptyResultException;
import ru.otus.library.exceptions.InvalidDataForUpdateException;
import ru.otus.library.services.AuthorService;
import ru.otus.library.services.BookService;
import ru.otus.library.services.GenreService;

@ShellComponent
@RequiredArgsConstructor
public class ApplicationCommands {
    private final BookService bookService;
    private final GenreService genreService;
    private final AuthorService authorService;
    private final BookViewConverter bookViewConverter;
    private final GenreViewConverter genreViewConverter;
    private final AuthorViewConverter authorViewConverter;
    private static final String CHECK_THE_OTHERS_INSERT_PROMPT = "check data for insertion";
    private static final String CHECK_THE_OTHERS_UPDATE_PROMPT = "check data for update";
    private static final String COMPLETED_INSERT = "your insert completed";
    private static final String COMPLETED_UPDATE = "your update completed";
    private static final String CHECK_ID_PROMPT = "Please check your id";


    @ShellMethod(value = "get all genres", key = {"get-genres", "genres"})
    public String getAllGenres() {
        try {
            return genreViewConverter.getViewGenres(genreService.getAll());
        } catch (EmptyResultException exception) {
            return exception.getMessage() + CHECK_ID_PROMPT;
        }
    }

    @ShellMethod(value = "get all authors", key = {"get-authors", "authors"})
    public String getAllAuthors() {
        try {
            return authorViewConverter.getViewAuthors(authorService.getAll());
        } catch (EmptyResultException exception) {
            return exception.getMessage() + CHECK_ID_PROMPT;
        }
    }

    @ShellMethod(value = "get all books", key = {"get-books", "books"})
    public String getAllBooks() {
        try {
            return bookViewConverter.getViewBooks(bookService.getAll());
        } catch (EmptyResultException exception) {
            return exception.getMessage() + ", " + CHECK_ID_PROMPT;
        }
    }

    @ShellMethod(value = "insert book", key = {"insert-book", "ib"})
    public String insert(String name, long authorId, long genreId) {
        try {
            bookService.insert(name, authorId, genreId);
        } catch (InvalidDataForUpdateException exception) {
            return exception.getMessage() + ", " + CHECK_THE_OTHERS_INSERT_PROMPT;
        } catch (CannotUpdateException exception) {
            return exception.getMessage();
        }
        return COMPLETED_INSERT;
    }

    @ShellMethod(value = "delete book", key = {"delete-book", "db"})
    public void deleteBookById(long id) {
        bookService.deleteById(id);
    }

    @ShellMethod(value = "get book", key = {"get-book", "gb"})
    public String getBookById(long id) {
        try {
            var book = bookService.getById(id);
            return bookViewConverter.getViewBook(book);
        } catch (EmptyResultException exception) {
            return exception.getMessage() + CHECK_ID_PROMPT;
        }
    }

    @ShellMethod(value = "update book", key = {"update-book", "ub"})
    public String updateBookById(long id, String name, long genreId, long authorId) {
        try {
            bookService.update(id, name, genreId, authorId);
        } catch (InvalidDataForUpdateException exception) {
            return exception.getMessage() + ", " + CHECK_THE_OTHERS_UPDATE_PROMPT;
        } catch (CannotUpdateException exception) {
            return exception.getMessage();
        }
        return COMPLETED_UPDATE;
    }
}
