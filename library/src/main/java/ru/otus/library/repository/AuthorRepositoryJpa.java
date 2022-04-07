package ru.otus.library.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.library.domain.Author;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class AuthorRepositoryJpa implements AuthorRepository {
    @PersistenceContext
    private final EntityManager entityManager;

    @Override
    public List<Author> getAll() {
        var query = entityManager.createQuery("Select a from Author a", Author.class);
        return query.getResultList();
    }

    @Override
    public Optional<Author> getById(long id){
        return Optional.ofNullable(entityManager.find(Author.class,id));
    }
}
