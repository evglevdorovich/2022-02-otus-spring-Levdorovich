package ru.otus.batch.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.batch.model.mongo.MongoGenre;

public interface MongoGenreRepository extends MongoRepository<MongoGenre, String> {
}
