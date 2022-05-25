package ru.otus.library.repository;

import ru.otus.library.model.Comment;

import java.util.List;

public interface CommentRepositoryCustom {
    Comment saveComment(Comment comment);
    Comment updateComment(Comment comment);
    List<Comment> findByBookId(String bookId);
    boolean deleteByBookAndCommentId(String bookId, String commentId);
}
