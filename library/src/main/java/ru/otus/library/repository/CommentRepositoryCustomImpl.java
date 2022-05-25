package ru.otus.library.repository;

import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.library.domain.Book;
import ru.otus.library.domain.Comment;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

@RequiredArgsConstructor
public class CommentRepositoryCustomImpl implements CommentRepositoryCustom {
    private final ReactiveMongoTemplate mongoTemplate;

    @Override
    public Mono<Comment> saveComment(Comment comment) {
        var bookId = comment.getBook().getId();
        comment.setId(new ObjectId().toHexString());
        var monoComment = Mono.just(comment);
        var update = new Update().addToSet("comments", comment);
        return mongoTemplate.updateFirst(Query.query(Criteria.where("_id").is(bookId)),
                update, "books").then(monoComment);
    }

    @Override
    public Flux<Comment> findByBookId(String bookId) {
        var aggregation = newAggregation(
                match(Criteria.where("_id").is(bookId)),
                unwind("comments"),
                project().and("_id").as("book._id").and("comments._id").as("_id")
                        .and("comments.text").as("text").and("name").as("book.name"));
        return mongoTemplate.aggregate(aggregation, Book.class, Comment.class);
    }
}
