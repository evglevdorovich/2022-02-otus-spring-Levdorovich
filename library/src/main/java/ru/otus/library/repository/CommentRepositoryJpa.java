package ru.otus.library.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.library.domain.Comment;
import ru.otus.library.exceptions.InvalidDataForUpdateException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CommentRepositoryJpa implements CommentRepository {
    @PersistenceContext
    private final EntityManager entityManager;

    @Override
    public Optional<Comment> getById(long id) {
        var entityGraph = entityManager.getEntityGraph("comment-book-entity-graph");
        Map<String, Object> properties = Map.of("javax.persistence.fetchgraph", entityGraph);
        return Optional.ofNullable(entityManager.find(Comment.class, id, properties));
    }

    @Override
    public Comment saveOrUpdate(Comment comment) {
        if (comment.getId() <= 0) {
            entityManager.persist(comment);
            return comment;
        }
        return entityManager.merge(comment);
    }

    @Override
    public void deleteById(long id) {
        var comment = entityManager.find(Comment.class, id);
        if (comment == null) {
            throw new InvalidDataForUpdateException();
        } else {
            entityManager.remove(comment);
        }
    }

    @Override
    public List<Comment> getByBookId(long bookId) {
        var entityGraph = entityManager.getEntityGraph("comment-book-entity-graph");
        var query = entityManager.createQuery("select c from Comment c where c.book.id = :bookId",
                Comment.class);
        query.setParameter("bookId", bookId);
        query.setHint("javax.persistence.fetchgraph", entityGraph);
        return query.getResultList();
    }
}
