package ru.otus.batch.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.batch.model.mongo.MongoAuthor;

public interface MongoAuthorRepository extends MongoRepository<MongoAuthor, String> {
}
