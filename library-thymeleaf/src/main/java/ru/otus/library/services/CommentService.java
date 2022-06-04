package ru.otus.library.services;

import ru.otus.library.domain.Comment;

import java.util.List;

public interface CommentService {
    Comment getById(long id);

    void deleteById(long id);

    void save(long bookId, String text);

    void update(long commentId, String updatedText);

    List<Comment> getByBookId(long bookId);
}
