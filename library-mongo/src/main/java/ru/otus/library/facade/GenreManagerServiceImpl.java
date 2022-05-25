package ru.otus.library.facade;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.library.converter.GenreViewConverter;
import ru.otus.library.services.GenreService;

@Service
@RequiredArgsConstructor
public class GenreManagerServiceImpl implements GenreManagerService {
    private final GenreService genreService;
    private final GenreViewConverter genreViewConverter;

    @Override
    public String getAllView() {
        return genreViewConverter.getViewGenres(genreService.findAll());
    }
}
