package ru.otus.library.repository;

import com.mongodb.client.result.DeleteResult;
import reactor.core.publisher.Mono;
import ru.otus.library.domain.Book;

import java.util.Optional;

public interface BookRepositoryCustom {
    Mono<Book> update(Book book);
}
