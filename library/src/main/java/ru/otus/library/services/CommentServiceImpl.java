package ru.otus.library.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.library.domain.Comment;
import ru.otus.library.exceptions.EmptyResultException;
import ru.otus.library.exceptions.InvalidDataForUpdateException;
import ru.otus.library.repository.BookRepository;
import ru.otus.library.repository.CommentRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final BookRepository bookRepository;

    @Override
    @Transactional(readOnly = true)
    public Comment getById(long id) {
        return commentRepository.getById(id).orElseThrow(EmptyResultException::new);
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        commentRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void save(long bookId, String text) {
        var book = bookRepository.getById(bookId).orElseThrow(InvalidDataForUpdateException::new);
        var comment = new Comment(book, text);
        commentRepository.saveOrUpdate(comment);
    }

    @Override
    @Transactional
    public void update(long commentId, String updatedText) {
        var comment = commentRepository.getById(commentId).orElseThrow(InvalidDataForUpdateException::new);
        comment.setText(updatedText);
        commentRepository.saveOrUpdate(comment);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Comment> getByBookId(long bookId) {
        return commentRepository.getByBookId(bookId);
    }
}
