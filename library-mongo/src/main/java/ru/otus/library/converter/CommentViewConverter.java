package ru.otus.library.converter;

import ru.otus.library.model.Comment;

import java.util.List;

public interface CommentViewConverter {
    String getViewComment(Comment comment);

    String getViewComments(List<Comment> comments);
}
