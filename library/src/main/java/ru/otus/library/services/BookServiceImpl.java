package ru.otus.library.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.library.dao.BookDao;
import ru.otus.library.domain.Author;
import ru.otus.library.domain.Book;
import ru.otus.library.domain.Genre;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookDao bookDao;

    @Transactional(readOnly = true)
    @Override
    public List<Book> getAll() {
        return bookDao.getAll();
    }

    @Transactional
    @Override
    public void update(long id, String bookName, long genreId, long authorId) {
        bookDao.update(new Book(id, bookName, new Author(authorId), new Genre(genreId)));
    }

    @Transactional
    @Override
    public void insert(String name, long genreId, long authorId) {
        bookDao.insert(new Book(name, new Author(authorId), new Genre(genreId)));
    }

    @Transactional
    @Override
    public void deleteById(long id) {
        bookDao.deleteById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public Book getById(long id) {
        return bookDao.getById(id);
    }


}
