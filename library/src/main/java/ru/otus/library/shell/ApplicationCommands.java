package ru.otus.library.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.library.converter.AuthorViewConverter;
import ru.otus.library.converter.BookViewConverter;
import ru.otus.library.converter.CommentViewConverter;
import ru.otus.library.converter.GenreViewConverter;
import ru.otus.library.exceptions.CannotUpdateException;
import ru.otus.library.exceptions.EmptyResultException;
import ru.otus.library.exceptions.InvalidDataForUpdateException;
import ru.otus.library.services.AuthorService;
import ru.otus.library.services.BookService;
import ru.otus.library.services.CommentService;
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
    private final CommentService commentService;
    private final CommentViewConverter commentViewConverter;

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
        return genreViewConverter.getViewGenres(genreService.getAll());
    }

    @ShellMethod(value = "get all authors", key = {"get-authors", "authors"})
    public String getAllAuthors() {
        return authorViewConverter.getViewAuthors(authorService.getAll());
    }

    @ShellMethod(value = "get all books", key = {"get-books", "books"})
    public String getAllBooks() {
        return bookViewConverter.getViewBooks(bookService.getAll());
    }

    @ShellMethod(value = "save book", key = {"save-book", "ib"})
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
    public String deleteBookById(long id) {
        try {
            bookService.deleteById(id);
            return COMPLETED_DELETE;
        } catch (InvalidDataForUpdateException ex) {
            return ex.getMessage() + " " + CHECK_ID_PROMPT;
        }
    }

    @ShellMethod(value = "get book", key = {"get-book", "gb"})
    public String getBookById(long id) {
        try {
            var book = bookService.getById(id);
            return bookViewConverter.getViewBook(book);
        } catch (EmptyResultException exception) {
            return exception.getMessage() + " " + CHECK_ID_PROMPT;
        }
    }

    @ShellMethod(value = "update book", key = {"update-book", "ub"})
    public String updateBookById(long id, String name, long genreId, long authorId) {
        try {
            bookService.update(id, name, genreId, authorId);
        } catch (InvalidDataForUpdateException exception) {
            return exception.getMessage() + ", " + CHECK_THE_OTHERS_UPDATE_PROMPT;
        }
        return COMPLETED_UPDATE;
    }

    @ShellMethod(value = "get comment", key = {"get-comment", "gc"})
    public String getCommentById(long id) {
        try {
            return commentViewConverter.getViewComment(commentService.getById(id));
        } catch (EmptyResultException exception) {
            return exception.getMessage() + " " + CHECK_ID_PROMPT;
        }
    }

    @ShellMethod(value = "delete comment", key = {"delete-comment", "dc"})
    public String deleteCommentById(long id) {
        try {
            commentService.deleteById(id);
            return COMPLETED_DELETE;
        } catch (InvalidDataForUpdateException ex) {
            return ex.getMessage() + " " + CHECK_ID_PROMPT;
        }
    }

    @ShellMethod(value = "get comments by book_id", key = {"get-comments-by-book-id", "gcbi"})
    public String getCommentsByBookId(long bookId) {
        var comments = commentService.getByBookId(bookId);
        return comments.isEmpty() ? EMPTY_LIST : commentViewConverter.getViewComments(comments);
    }

    @ShellMethod(value = "add comment", key = {"add-comment", "ac"})
    public String addComment(long bookId, String text) {
        try {
            commentService.save(bookId, text);
            return COMPLETED_ADD;
        } catch (InvalidDataForUpdateException exception) {
            return exception.getMessage() + ", " + CHECK_THE_OTHERS_UPDATE_PROMPT;
        }
    }

    @ShellMethod(value = "change comment", key = {"change-comment", "cc"})
    public String changeComment(long commentId, String updatedText) {
        try {
            commentService.update(commentId, updatedText);
            return COMPLETED_UPDATE;
        } catch (InvalidDataForUpdateException exception) {
            return exception.getMessage() + ", " + CHECK_THE_OTHERS_UPDATE_PROMPT;
        }
    }
}
