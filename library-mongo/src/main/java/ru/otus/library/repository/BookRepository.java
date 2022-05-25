package ru.otus.library.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.library.model.Book;


public interface BookRepository extends MongoRepository<Book, String>, BookRepositoryCustom {
}
