package ru.otus.library.services;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.library.dao.BookDao;
import ru.otus.library.domain.Author;
import ru.otus.library.domain.Book;
import ru.otus.library.domain.Genre;

import static org.mockito.Mockito.verify;

@SpringBootTest(classes = BookServiceImpl.class)
@DisplayName("Book service impl should:  ")
class BookServiceImplTest {
    @Autowired
    private BookServiceImpl bookService;
    @MockBean
    private BookDao bookDao;

    @DisplayName("call dao get all")
    @Test
    void shouldCallDaoGetAll() {
        bookService.getAll();
        verify(bookDao).getAll();
    }

    @DisplayName("call dao get by id")
    @Test
    void shouldCallDaoGetById() {
        var id = 1;
        bookService.getById(id);
        verify(bookDao).getById(id);
    }

    @DisplayName("call dao update book")
    @Test
    void shouldCallDaoUpdateBook() {
        var book = new Book(1, "book", new Author(1), new Genre(1));
        bookService.update(book.getId(), book.getName(), book.getGenre().getId(), book.getAuthor().getId());
        verify(bookDao).update(book);
    }

    @DisplayName("call dao delete by id")
    @Test
    void shouldCallDaoDeleteByIdBook() {
        var id = 1;
        bookService.deleteById(id);
        verify(bookDao).deleteById(id);
    }

    @DisplayName("call dao insert book")
    @Test
    void shouldCallDaoInsertBook() {
        var book = new Book("book", new Author(1), new Genre(1));
        bookService.insert(book.getName(), book.getAuthor().getId(), book.getGenre().getId());
        verify(bookDao).insert(book);
    }
}