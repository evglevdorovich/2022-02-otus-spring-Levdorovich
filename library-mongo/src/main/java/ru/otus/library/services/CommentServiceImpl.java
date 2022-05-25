package ru.otus.library.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.library.exceptions.InvalidDataForUpdateException;
import ru.otus.library.model.Comment;
import ru.otus.library.repository.BookRepository;
import ru.otus.library.repository.CommentRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final BookRepository bookRepository;

    @Override
    @Transactional
    public void deleteByBookAndCommentId(String bookId, String commentId) {
        if (!commentRepository.deleteByBookAndCommentId(bookId, commentId)) {
            throw new InvalidDataForUpdateException();
        }
    }

    @Override
    @Transactional
    public void saveComment(String bookId, String text) {
        var book = bookRepository.findPartialBookById(bookId).orElseThrow(InvalidDataForUpdateException::new);
        var comment = new Comment(book, text);
        commentRepository.saveComment(comment);
    }

    @Override
    @Transactional
    public void update(String bookId, String commentId, String updatedText) {
        var book = bookRepository.findPartialBookById(bookId).orElseThrow(InvalidDataForUpdateException::new);
        var comment = new Comment(commentId, updatedText, book);
        commentRepository.updateComment(comment);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Comment> findByBookId(String bookId) {
        return commentRepository.findByBookId(bookId);
    }
}
