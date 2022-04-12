package ru.otus.library.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.library.domain.Comment;
import ru.otus.library.exceptions.InvalidDataForUpdateException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
@RequiredArgsConstructor
public class CommentRepositoryCustomImpl implements CommentRepositoryCustom {
    @PersistenceContext
    private final EntityManager entityManager;

    @Override
    public void deleteExistingCommentById(long id) {
        var comment = entityManager.find(Comment.class, id);
        if (comment == null) {
            throw new InvalidDataForUpdateException();
        }
        entityManager.remove(comment);
    }
}
