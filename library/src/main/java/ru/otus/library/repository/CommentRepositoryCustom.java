package ru.otus.library.repository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.library.domain.Comment;

public interface CommentRepositoryCustom {
    Mono<Comment> saveComment(Comment comment);

    Flux<Comment> findByBookId(String bookId);
}
