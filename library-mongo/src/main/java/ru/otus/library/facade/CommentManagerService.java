package ru.otus.library.facade;

public interface CommentManagerService {

    void deleteByBookAndCommentId(String bookId, String commentId);

    String getViewByBookId(String bookId);

    void saveComment(String bookId, String text);

    void update(String bookId, String commentId, String updatedText);
}
