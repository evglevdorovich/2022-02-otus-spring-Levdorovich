package ru.otus.library.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import ru.otus.library.domain.MongoBook;


public interface BookRepository extends ReactiveMongoRepository<MongoBook, String> {
    
}
