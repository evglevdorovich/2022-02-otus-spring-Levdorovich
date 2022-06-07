package ru.otus.library.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.otus.library.dto.BookForUpdateDto;
import ru.otus.library.services.AuthorService;
import ru.otus.library.services.BookService;
import ru.otus.library.services.GenreService;

@Controller
@RequiredArgsConstructor
public class BookController {

    private final AuthorService authorService;
    private final GenreService genreService;
    private final BookService bookService;

    @GetMapping("/books/{id}")
    public String books(@PathVariable(name = "id") long id, Model model) {
        var book = bookService.getById(id);
        var genresWithoutBooksGenre = genreService.getAllExceptBooksGenre(book);
        var authorsWithoutBooksAuthor = authorService.getAllExceptBooksAuthor(book);

        model.addAttribute("book", book);
        model.addAttribute("genres", genresWithoutBooksGenre);
        model.addAttribute("authors", authorsWithoutBooksAuthor);
        return "book";
    }

    @PostMapping("/books/edit/{id}")
    public String editBook(@PathVariable(name = "id") long id, BookForUpdateDto bookDto) {
        bookService.update(id, bookDto.getName(), bookDto.getGenreId(), bookDto.getAuthorId());
        return "redirect:/books/" + id;
    }

    @PostMapping("/books/delete/{id}")
    public String deleteBook(@PathVariable(name = "id") long id) {
        bookService.deleteById(id);
        return "redirect:/";
    }

    @PostMapping("/books")
    public String createBook(BookForUpdateDto bookDto) {
        bookService.insert(bookDto.getName(), bookDto.getGenreId(), bookDto.getAuthorId());
        return "redirect:/";
    }
}
