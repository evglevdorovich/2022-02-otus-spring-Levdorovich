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
    public void insert(String name, String genreName, String authorName) {
        bookService.insert(name, authorName, genreName);
    }

    @Override
    public void deleteById(String id) {
        bookService.deleteById(id);
    }

    @Override
    public String getViewById(String id) {
        return bookViewConverter.getViewBook(bookService.getById(id));
    }

    @Override
    public void update(String id, String bookName, String genreName, String authorName) {
        bookService.update(id, bookName, genreName, authorName);
    }
}
