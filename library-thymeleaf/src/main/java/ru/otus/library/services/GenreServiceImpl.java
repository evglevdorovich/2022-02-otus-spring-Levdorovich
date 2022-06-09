package ru.otus.library.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.library.domain.Book;
import ru.otus.library.domain.Genre;
import ru.otus.library.repository.GenreRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class GenreServiceImpl implements GenreService {
    private final GenreRepository genreRepository;

    @Transactional(readOnly = true)
    @Override
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
    public List<Genre> getAll() {
        return genreRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
    public List<Genre> getAllExceptBooksGenre(Book book) {
        var genres = genreRepository.findAll();
        genres.remove(book.getGenre());
        return genres;
    }
}
