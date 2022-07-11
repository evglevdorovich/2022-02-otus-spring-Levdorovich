package ru.otus.library_rest.projections;

import org.springframework.data.rest.core.config.Projection;
import ru.otus.library_rest.domain.Comment;

@Projection(name = "commentProjection", types = {Comment.class})
public interface CommentProjection {
    String getText();
}
