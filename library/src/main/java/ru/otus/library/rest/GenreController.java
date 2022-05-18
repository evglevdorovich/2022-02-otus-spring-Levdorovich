package ru.otus.library.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.library.domain.Author;
import ru.otus.library.domain.Genre;
import ru.otus.library.services.GenreService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class GenreController {
    private final GenreService genreService;

    @GetMapping("/api/genres")
    public List<Genre> authors(){
        return genreService.getAll();
    }
}
