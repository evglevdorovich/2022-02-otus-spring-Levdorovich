package ru.otus.library.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.library.domain.Comment;

public interface CommentRepository extends ReactiveMongoRepository<Comment, String> {

    Flux<Comment> findByBookId(String bookId);
    Mono<Void> deleteByBookId(String bookId);

}
