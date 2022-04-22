package ru.otus.library.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.library.exceptions.CannotUpdateException;
import ru.otus.library.exceptions.EmptyResultException;
import ru.otus.library.exceptions.InvalidDataForUpdateException;
import ru.otus.library.facade.AuthorManagerService;
import ru.otus.library.facade.BookManagerService;
import ru.otus.library.facade.CommentManagerService;
import ru.otus.library.facade.GenreManagerService;

@ShellComponent
@RequiredArgsConstructor
public class ApplicationCommands {
    private final BookManagerService bookManagerService;
    private final GenreManagerService genreManagerService;
    private final AuthorManagerService authorManagerService;
    private final CommentManagerService commentManagerService;

    private static final String CHECK_THE_OTHERS_INSERT_PROMPT = "check data for insertion";
    private static final String CHECK_THE_OTHERS_UPDATE_PROMPT = "check data for update";
    private static final String COMPLETED_INSERT = "your saveBook completed";
    private static final String COMPLETED_ADD = "your add completed";
    private static final String COMPLETED_UPDATE = "your update completed";
    private static final String COMPLETED_DELETE = "your delete completed";
    private static final String CHECK_ID_PROMPT = "Please check your id";
    private static final String EMPTY_LIST = "Your result is empty";


    @ShellMethod(value = "get all genres", key = {"get-genres", "genres"})
    public String getAllGenres() {
        return genreManagerService.getAllView();
    }

    @ShellMethod(value = "get all authors", key = {"get-authors", "authors"})
    public String getAllAuthors() {
        return authorManagerService.getAllView();
    }

    @ShellMethod(value = "get all books", key = {"get-books", "books"})
    public String getAllBooks() {
        return bookManagerService.getAllViews();
    }

    @ShellMethod(value = "saveComment book", key = {"saveComment-book", "ib"})
    public String insert(String name, String authorName, String genreName) {
        try {
            bookManagerService.insert(name, authorName, genreName);
        } catch (InvalidDataForUpdateException exception) {
            return exception.getMessage() + ", " + CHECK_THE_OTHERS_INSERT_PROMPT;
        } catch (CannotUpdateException exception) {
            return exception.getMessage();
        }
        return COMPLETED_INSERT;
    }

    @ShellMethod(value = "delete book", key = {"delete-book", "db"})
    public String deleteBookById(String id) {
        try {
            bookManagerService.deleteById(id);
            return COMPLETED_DELETE;
        } catch (InvalidDataForUpdateException ex) {
            return ex.getMessage() + " " + CHECK_ID_PROMPT;
        }
    }

    @ShellMethod(value = "get book", key = {"get-book", "gb"})
    public String getBookById(String id) {
        try {
            return bookManagerService.getViewById(id);
        } catch (EmptyResultException exception) {
            return exception.getMessage() + " " + CHECK_ID_PROMPT;
        }
    }

    @ShellMethod(value = "update book", key = {"update-book", "ub"})
    public String updateBookById(String id, String name, String genreName, String authorName) {
        try {
            bookManagerService.update(id, name, genreName, authorName);
        } catch (InvalidDataForUpdateException exception) {
            return exception.getMessage() + ", " + CHECK_THE_OTHERS_UPDATE_PROMPT;
        }
        return COMPLETED_UPDATE;
    }

    @ShellMethod(value = "delete comment", key = {"delete-comment", "dc"})
    public String deleteCommentById(String bookId, String commentId) {
        try {
            commentManagerService.deleteByBookAndCommentId(bookId, commentId);
            return COMPLETED_DELETE;
        } catch (InvalidDataForUpdateException ex) {
            return ex.getMessage() + " " + CHECK_ID_PROMPT;
        }
    }

    @ShellMethod(value = "get comments by book_id", key = {"get-comments-by-book-id", "gcbi"})
    public String getCommentsByBookId(String bookId) {
        var comments = commentManagerService.getViewByBookId(bookId);
        return comments.isEmpty() ? EMPTY_LIST : comments;
    }

    @ShellMethod(value = "add comment", key = {"add-comment", "ac"})
    public String addComment(String bookId, String text) {
        try {
            commentManagerService.saveComment(bookId, text);
            return COMPLETED_ADD;
        } catch (InvalidDataForUpdateException exception) {
            return exception.getMessage() + ", " + CHECK_THE_OTHERS_UPDATE_PROMPT;
        }
    }

    @ShellMethod(value = "change comment", key = {"change-comment", "cc"})
    public String changeComment(String bookId, String commentId, String updatedText) {
        try {
            commentManagerService.update(bookId, commentId, updatedText);
            return COMPLETED_UPDATE;
        } catch (InvalidDataForUpdateException exception) {
            return exception.getMessage() + ", " + CHECK_THE_OTHERS_UPDATE_PROMPT;
        }
    }
}
