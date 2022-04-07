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
    private static final String COMPLETED_INSERT = "your save completed";
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

    @ShellMethod(value = "save book", key = {"save-book", "ib"})
    public String insert(String name, long authorId, long genreId) {
        try {
            bookManagerService.insert(name, authorId, genreId);
        } catch (InvalidDataForUpdateException exception) {
            return exception.getMessage() + ", " + CHECK_THE_OTHERS_INSERT_PROMPT;
        } catch (CannotUpdateException exception) {
            return exception.getMessage();
        }
        return COMPLETED_INSERT;
    }

    @ShellMethod(value = "delete book", key = {"delete-book", "db"})
    public String deleteBookById(long id) {
        try {
            bookManagerService.deleteById(id);
            return COMPLETED_DELETE;
        } catch (InvalidDataForUpdateException ex) {
            return ex.getMessage() + " " + CHECK_ID_PROMPT;
        }
    }

    @ShellMethod(value = "get book", key = {"get-book", "gb"})
    public String getBookById(long id) {
        try {
            return bookManagerService.getViewById(id);
        } catch (EmptyResultException exception) {
            return exception.getMessage() + " " + CHECK_ID_PROMPT;
        }
    }

    @ShellMethod(value = "update book", key = {"update-book", "ub"})
    public String updateBookById(long id, String name, long genreId, long authorId) {
        try {
            bookManagerService.updateById(id, name, genreId, authorId);
        } catch (InvalidDataForUpdateException exception) {
            return exception.getMessage() + ", " + CHECK_THE_OTHERS_UPDATE_PROMPT;
        }
        return COMPLETED_UPDATE;
    }

    @ShellMethod(value = "get comment", key = {"get-comment", "gc"})
    public String getCommentById(long id) {
        try {
            return commentManagerService.getViewById(id);
        } catch (EmptyResultException exception) {
            return exception.getMessage() + " " + CHECK_ID_PROMPT;
        }
    }

    @ShellMethod(value = "delete comment", key = {"delete-comment", "dc"})
    public String deleteCommentById(long id) {
        try {
            commentManagerService.deleteById(id);
            return COMPLETED_DELETE;
        } catch (InvalidDataForUpdateException ex) {
            return ex.getMessage() + " " + CHECK_ID_PROMPT;
        }
    }

    @ShellMethod(value = "get comments by book_id", key = {"get-comments-by-book-id", "gcbi"})
    public String getCommentsByBookId(long bookId) {
        var comments = commentManagerService.getViewByBookId(bookId);
        return comments.isEmpty() ? EMPTY_LIST : comments;
    }

    @ShellMethod(value = "add comment", key = {"add-comment", "ac"})
    public String addComment(long bookId, String text) {
        try {
            commentManagerService.save(bookId, text);
            return COMPLETED_ADD;
        } catch (InvalidDataForUpdateException exception) {
            return exception.getMessage() + ", " + CHECK_THE_OTHERS_UPDATE_PROMPT;
        }
    }

    @ShellMethod(value = "change comment", key = {"change-comment", "cc"})
    public String changeComment(long commentId, String updatedText) {
        try {
            commentManagerService.update(commentId, updatedText);
            return COMPLETED_UPDATE;
        } catch (InvalidDataForUpdateException exception) {
            return exception.getMessage() + ", " + CHECK_THE_OTHERS_UPDATE_PROMPT;
        }
    }
}
