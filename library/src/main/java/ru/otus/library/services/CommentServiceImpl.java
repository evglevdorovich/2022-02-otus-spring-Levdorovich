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
        return commentRepository.findById(id).orElseThrow(EmptyResultException::new);
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        commentRepository.deleteExistingCommentById(id);
    }

    @Override
    @Transactional
    public void save(long bookId, String text) {
        var book = bookRepository.findById(bookId).orElseThrow(InvalidDataForUpdateException::new);
        var comment = new Comment(book, text);
        commentRepository.save(comment);
    }

    @Override
    @Transactional
    public void update(long commentId, String updatedText) {
        var comment = commentRepository.findById(commentId).orElseThrow(InvalidDataForUpdateException::new);
        comment.setText(updatedText);
        commentRepository.save(comment);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Comment> getByBookId(long bookId) {
        return commentRepository.findAllByBookId(bookId);
    }
}
