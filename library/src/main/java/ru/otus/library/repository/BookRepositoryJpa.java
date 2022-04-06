package ru.otus.library.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.library.domain.Book;
import ru.otus.library.exceptions.InvalidDataForUpdateException;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class BookRepositoryJpa implements BookRepository {
    private final EntityManager entityManager;

    @Override
    public List<Book> getAll() {
        var entityGraph = entityManager.getEntityGraph("book-authors-genres-entity-graph");
        var query = entityManager.createQuery("select b from Book b", Book.class);
        query.setHint("javax.persistence.fetchgraph", entityGraph);
        return query.getResultList();
    }

    @Override
    public Optional<Book> getById(long id) {
        var entityGraph = entityManager.getEntityGraph("book-authors-genres-entity-graph");
        Map<String, Object> properties = Map.of("javax.persistence.fetchgraph", entityGraph);
        var book = entityManager.find(Book.class, id, properties);
        return Optional.ofNullable(book);
    }

    @Override
    public Book saveOrUpdate(Book book) {
        if (book.getId() <= 0) {
            entityManager.persist(book);
            return book;
        }
        return entityManager.merge(book);
    }

    @Override
    public void deleteById(long id) {
        var book = entityManager.find(Book.class, id);
        if (book == null) {
            throw new InvalidDataForUpdateException();
        } else {
            entityManager.remove(book);
        }
    }

}
