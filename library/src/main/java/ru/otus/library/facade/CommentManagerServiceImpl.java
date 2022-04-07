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
    public String getViewById(long id) {
        return commentViewConverter.getViewComment(commentService.getById(id));
    }

    @Override
    public void deleteById(long id) {
        commentService.deleteById(id);
    }

    @Override
    public String getViewByBookId(long bookId) {
        return commentViewConverter.getViewComments(commentService.getByBookId(bookId));
    }

    @Override
    public void save(long bookId, String text) {
        commentService.save(bookId, text);
    }

    @Override
    public void update(long commentId, String updatedText) {
        commentService.update(commentId, updatedText);
    }
}
