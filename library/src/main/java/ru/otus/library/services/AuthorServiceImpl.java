package ru.otus.library.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.library.dao.AuthorDao;
import ru.otus.library.domain.Author;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AuthorServiceImpl implements AuthorService {
    private final AuthorDao authorDao;

    @Transactional
    @Override
    public List<Author> getAll() {
        return authorDao.getAll();
    }
}
