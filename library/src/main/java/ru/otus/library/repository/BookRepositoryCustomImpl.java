package ru.otus.library.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import reactor.core.publisher.Mono;
import ru.otus.library.domain.Book;

@RequiredArgsConstructor
public class BookRepositoryCustomImpl implements BookRepositoryCustom {
    private final ReactiveMongoTemplate mongoTemplate;

    @Override
    public Mono<Book> update(Book book) {
        return mongoTemplate.findAndModify(Query.query(Criteria.where("_id").is(book.getId())),
                new Update().set("name", book.getName()).set("author", book.getAuthor()).set("genre", book.getGenre()),
                Book.class);
    }

    @Override
    public Mono<Void> deleteBookById(String id) {
        return mongoTemplate.remove(Query.query(Criteria.where("_id").is(id)), Book.class).then();
    }
}