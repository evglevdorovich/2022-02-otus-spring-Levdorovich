package ru.otus.library.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.library.model.Genre;

import java.util.Optional;

public interface GenreRepository extends MongoRepository<Genre,String> {
    Optional<Genre> findByName(String name);
}
