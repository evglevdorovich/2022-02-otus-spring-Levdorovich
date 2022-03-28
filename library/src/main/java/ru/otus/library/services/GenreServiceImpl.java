package ru.otus.library.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.library.dao.GenreDao;
import ru.otus.library.domain.Genre;

import java.util.List;

@RequiredArgsConstructor
@Service
public class GenreServiceImpl implements GenreService {
    private final GenreDao genreDao;

    @Transactional
    @Override
    public List<Genre> getAll() {
        return genreDao.getAll();
    }
}
