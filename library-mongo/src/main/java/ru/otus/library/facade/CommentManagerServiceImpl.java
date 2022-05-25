package ru.otus.library.facade;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.library.converter.CommentViewConverter;
import ru.otus.library.services.CommentService;

@Service
@RequiredArgsConstructor
public class CommentManagerServiceImpl implements CommentManagerService {
    private final CommentViewConverter commentViewConverter;
    private final CommentService commentService;

    @Override
    public void deleteByBookAndCommentId(String bookId, String commentId) {
        commentService.deleteByBookAndCommentId(bookId, commentId);
    }

    @Override
    public String getViewByBookId(String bookId) {
        return commentViewConverter.getViewComments(commentService.findByBookId(bookId));
    }

    @Override
    public void saveComment(String bookId, String text) {
        commentService.saveComment(bookId, text);
    }

    @Override
    public void update(String bookId, String commentId, String updatedText) {
        commentService.update(bookId, commentId, updatedText);
    }
}
