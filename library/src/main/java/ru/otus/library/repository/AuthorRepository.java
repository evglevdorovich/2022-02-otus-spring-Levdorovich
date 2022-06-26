package ru.otus.library.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import ru.otus.library.domain.MongoAuthor;

public interface AuthorRepository extends ReactiveMongoRepository<MongoAuthor, String> {
}
