package ru.otus.library.repository;

import ru.otus.library.domain.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentRepository {

    Optional<Comment> getById(long id);

    Comment saveOrUpdate(Comment comment);

    void deleteById(long id);

    List<Comment> getByBookId(long bookId);
}
