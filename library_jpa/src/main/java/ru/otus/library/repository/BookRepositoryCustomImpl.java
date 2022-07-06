package ru.otus.library.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.library.domain.Book;
import ru.otus.library.exceptions.InvalidDataForUpdateException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
@RequiredArgsConstructor
public class BookRepositoryCustomImpl implements BookRepositoryCustom {

    @PersistenceContext
    private final EntityManager entityManager;

    @Override
    public void deleteExistingBookById(long id) {
        var book = entityManager.find(Book.class, id);
        if (book == null) {
            throw new InvalidDataForUpdateException();
        }
        entityManager.remove(book);
    }
}
