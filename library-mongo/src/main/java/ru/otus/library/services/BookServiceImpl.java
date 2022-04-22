package ru.otus.library.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.library.exceptions.EmptyResultException;
import ru.otus.library.exceptions.InvalidDataForUpdateException;
import ru.otus.library.model.Book;
import ru.otus.library.repository.AuthorRepository;
import ru.otus.library.repository.BookRepository;
import ru.otus.library.repository.GenreRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final GenreRepository genreRepository;
    private final AuthorRepository authorRepository;

    @Transactional(readOnly = true)
    @Override
    public List<Book> getAll() {
        return bookRepository.findAll();
    }

    @Transactional
    @Override
    public void update(String id, String bookName, String genreName, String authorName) {
        var author = authorRepository.findByName(authorName).orElseThrow(InvalidDataForUpdateException::new);
        var genre = genreRepository.findByName(genreName).orElseThrow(InvalidDataForUpdateException::new);
        var book = bookRepository.findById(id).orElseThrow(InvalidDataForUpdateException::new);
        book.setGenre(genre);
        book.setAuthor(author);
        book.setName(bookName);
        bookRepository.update(book);
    }

    @Transactional
    @Override
    public void insert(String name, String genreName, String authorName) {
        var genre = genreRepository.findByName(genreName).orElseThrow(InvalidDataForUpdateException::new);
        var author = authorRepository.findByName(authorName).orElseThrow(InvalidDataForUpdateException::new);
        bookRepository.save(new Book(name, author, genre));
    }

    @Transactional
    @Override
    public void deleteById(String id) {
        if (!bookRepository.deleteBookById(id)) {
            throw new InvalidDataForUpdateException();
        }
    }

    @Transactional(readOnly = true)
    @Override
    public Book getById(String id) {
        return bookRepository.findById(id).orElseThrow(EmptyResultException::new);
    }


}
