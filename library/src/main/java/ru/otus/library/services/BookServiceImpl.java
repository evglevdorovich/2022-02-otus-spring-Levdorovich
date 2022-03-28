package ru.otus.library.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.library.dao.BookDao;
import ru.otus.library.domain.Book;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookDao bookDao;

    @Transactional
    @Override
    public List<Book> getAll() {
        return bookDao.getAll();
    }

    @Transactional
    @Override
    public void update(Book book) {
        bookDao.update(book);
    }

    @Transactional
    @Override
    public void insert(Book book) {
        bookDao.insert(book);
    }

    @Transactional
    @Override
    public void deleteById(long id) {
        bookDao.deleteById(id);
    }

    @Transactional
    @Override
    public Book getById(long id) {
        return bookDao.getById(id);
    }


}
