package ru.otus.library.services;

import ru.otus.library.model.Comment;

import java.util.List;

public interface CommentService {

    void deleteByBookAndCommentId(String bookId, String commentId);

    void saveComment(String bookId, String text);

    void update(String bookId, String commentId, String updatedText);

    List<Comment> findByBookId(String bookId);
}
