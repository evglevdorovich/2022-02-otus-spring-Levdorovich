package ru.otus.library_rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.otus.library_rest.domain.Comment;
import ru.otus.library_rest.projections.CommentProjection;

import java.util.List;

@RepositoryRestResource(excerptProjection = CommentProjection.class)
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByBookId(long bookId);
}
