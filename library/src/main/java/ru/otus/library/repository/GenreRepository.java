package ru.otus.library.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import ru.otus.library.domain.MongoGenre;

public interface GenreRepository extends ReactiveMongoRepository<MongoGenre, String> {
}
