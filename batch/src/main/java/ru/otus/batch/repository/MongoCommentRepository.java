package ru.otus.batch.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.batch.model.mongo.MongoComment;

public interface MongoCommentRepository extends MongoRepository<MongoComment, String> {
}
