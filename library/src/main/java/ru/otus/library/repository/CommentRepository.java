package ru.otus.library.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.library.domain.MongoComment;

public interface CommentRepository extends ReactiveMongoRepository<MongoComment, String> {

    Flux<MongoComment> findByBookId(String bookId);
    Mono<Void> deleteByBookId(String bookId);

}
