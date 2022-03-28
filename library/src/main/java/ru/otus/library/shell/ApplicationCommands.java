package ru.otus.library.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.library.converter.AuthorViewConverter;
import ru.otus.library.converter.BookViewConverter;
import ru.otus.library.converter.GenreViewConverter;
import ru.otus.library.dao.AuthorDao;
import ru.otus.library.dao.BookDao;
import ru.otus.library.dao.GenreDao;
import ru.otus.library.domain.Book;
import ru.otus.library.exceptions.CannotUpdateException;
import ru.otus.library.exceptions.EmptyResultException;
import ru.otus.library.exceptions.InvalidDataForUpdateException;

@ShellComponent
@RequiredArgsConstructor
public class ApplicationCommands {
    private final BookDao bookDao;
    private final GenreDao genreDao;
    private final AuthorDao authorDao;
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
            return genreViewConverter.getViewGenres(genreDao.getAll());
        } catch (EmptyResultException exception) {
            return exception.getMessage() + CHECK_ID_PROMPT;
        }
    }

    @ShellMethod(value = "get all authors", key = {"get-authors", "authors"})
    public String getAllAuthors() {
        try {
            return authorViewConverter.getViewAuthors(authorDao.getAll());
        } catch (EmptyResultException exception) {
            return exception.getMessage() + CHECK_ID_PROMPT;
        }
    }

    @ShellMethod(value = "get all books", key = {"get-books", "books"})
    public String getAllBooks() {
        try {
            return bookViewConverter.getViewBooks(bookDao.getAll());
        } catch (EmptyResultException exception) {
            return exception.getMessage() + ", " + CHECK_ID_PROMPT;
        }
    }

    @ShellMethod(value = "insert book", key = {"insert-book", "ib"})
    public String insert(String name, long authorId, long genreId, @ShellOption(defaultValue = "0") String id) {
        try {
            bookDao.insert(new Book(Integer.parseInt(id), name, authorId, genreId));
        } catch (InvalidDataForUpdateException exception) {
            return exception.getMessage() + ", " + CHECK_THE_OTHERS_INSERT_PROMPT;
        } catch (CannotUpdateException exception) {
            return exception.getMessage();
        }
        return COMPLETED_INSERT;
    }

    @ShellMethod(value = "delete book", key = {"delete-book", "db"})
    public void deleteBookById(long id) {
        bookDao.deleteById(id);
    }

    @ShellMethod(value = "get book", key = {"get-book", "gb"})
    public String getBookById(long id) {
        try {
            var book = bookDao.getById(id);
            return bookViewConverter.getViewBook(book);
        } catch (EmptyResultException exception) {
            return exception.getMessage() + CHECK_ID_PROMPT;
        }
    }

    @ShellMethod(value = "update book", key = {"update-book", "ub"})
    public String updateBookById(long id, String name, long genreId, long authorId) {
        try {
            bookDao.update(new Book(id, name, genreId, authorId));
        } catch (InvalidDataForUpdateException exception) {
            return exception.getMessage() + ", " + CHECK_THE_OTHERS_UPDATE_PROMPT;
        } catch (CannotUpdateException exception) {
            return exception.getMessage();
        }
        return COMPLETED_UPDATE;
    }
}
