package ru.otus.batch.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.batch.model.mongo.MongoBook;

public interface MongoBookRepository extends MongoRepository<MongoBook, String> {
}
