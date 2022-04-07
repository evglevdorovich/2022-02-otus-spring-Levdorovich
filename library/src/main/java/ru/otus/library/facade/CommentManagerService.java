package ru.otus.library.facade;

public interface CommentManagerService {
    String getViewById(long id);

    void deleteById(long id);

    String getViewByBookId(long bookId);

    void save(long bookId, String text);

    void update(long commentId, String updatedText);
}
