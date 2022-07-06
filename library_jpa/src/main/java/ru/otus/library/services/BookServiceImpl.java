package ru.otus.library.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.library.domain.Book;
import ru.otus.library.exceptions.EmptyResultException;
import ru.otus.library.exceptions.InvalidDataForUpdateException;
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
    public void update(long id, String name, long genreId, long authorId) {
        var author = authorRepository.findById(authorId).orElseThrow(InvalidDataForUpdateException::new);
        var genre = genreRepository.findById(genreId).orElseThrow(InvalidDataForUpdateException::new);
        var book = bookRepository.findById(id).orElseThrow(InvalidDataForUpdateException::new);
        book.setGenre(genre);
        book.setAuthor(author);
        book.setName(name);
        bookRepository.save(book);
    }

    @Transactional
    @Override
    public void insert(String name, long genreId, long authorId) {
        System.out.println(name + genreId + authorId);
        var genre = genreRepository.findById(genreId).orElseThrow(InvalidDataForUpdateException::new);
        var author = authorRepository.findById(authorId).orElseThrow(InvalidDataForUpdateException::new);
        bookRepository.save(new Book(name, author, genre));
    }

    @Transactional
    @Override
    public void deleteById(long id) {
        bookRepository.deleteExistingBookById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public Book getById(long id) {
        return bookRepository.findById(id).orElseThrow(EmptyResultException::new);
    }


}
