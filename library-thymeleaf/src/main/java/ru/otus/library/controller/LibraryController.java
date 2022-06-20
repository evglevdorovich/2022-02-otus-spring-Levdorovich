package ru.otus.library.controller;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.otus.library.dto.BookForViewDto;
import ru.otus.library.services.AuthorService;
import ru.otus.library.services.BookService;
import ru.otus.library.services.GenreService;

import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class LibraryController {

    private final AuthorService authorService;
    private final GenreService genreService;
    private final BookService bookService;
    private final ModelMapper modelMapper;

    @GetMapping("/")
    public String libraryPage(Model model) {
        var authors = authorService.getAll();
        var genres = genreService.getAll();
        var books = bookService.getAll().stream()
                .map(b -> modelMapper.map(b, BookForViewDto.class))
                .collect(Collectors.toList());

        model.addAttribute("authors", authors);
        model.addAttribute("genres", genres);
        model.addAttribute("books", books);
        return "library";
    }

}
