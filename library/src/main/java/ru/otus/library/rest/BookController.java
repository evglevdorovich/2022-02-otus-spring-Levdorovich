package ru.otus.library.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.otus.library.domain.Book;
import ru.otus.library.dto.BookForUpdateDto;
import ru.otus.library.services.BookService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping("/api/books/{id}")
    public Book book(@PathVariable(name = "id") long id) {
        return bookService.getById(id);
    }

    @GetMapping("/api/books")
    public List<Book> books() {
        return bookService.getAll();
    }

    @DeleteMapping("/api/books/{id}")
    public void deleteBook(@PathVariable(name = "id") long id) {
        bookService.deleteById(id);
    }

    @PostMapping("/api/books")
    public void createBook(@RequestBody BookForUpdateDto bookDto) {
        bookService.insert(bookDto.getName(), bookDto.getGenreId(), bookDto.getAuthorId());
    }

    @PutMapping("api/books/{id}")
    public void editBook(@PathVariable(name = "id") long id, @RequestBody BookForUpdateDto bookDto) {
        bookService.update(id, bookDto.getName(), bookDto.getGenreId(), bookDto.getAuthorId());
    }

}
