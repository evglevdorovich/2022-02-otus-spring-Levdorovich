package ru.otus.library.facade;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.library.converter.BookViewConverter;
import ru.otus.library.services.BookService;

@Service
@RequiredArgsConstructor
public class BookManagerServiceImpl implements BookManagerService {
    private final BookService bookService;
    private final BookViewConverter bookViewConverter;

    @Override
    public String getAllViews() {
        return bookViewConverter.getViewBooks(bookService.getAll());
    }

    @Override
    public void insert(String name, long authorId, long genreId) {
        bookService.insert(name, authorId, genreId);
    }

    @Override
    public void deleteById(long id) {
        bookService.deleteById(id);
    }

    @Override
    public String getViewById(long id) {
        return bookViewConverter.getViewBook(bookService.getById(id));
    }

    @Override
    public void updateById(long id, String name, long genreId, long authorId) {
        bookService.update(id, name, genreId, authorId);
    }
}
