package ru.otus.library.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import ru.otus.library.model.Book;

import java.util.Optional;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

@RequiredArgsConstructor
public class BookRepositoryCustomImpl implements BookRepositoryCustom {
    private final MongoTemplate mongoTemplate;

    @Override
    public Book update(Book book) {
        mongoTemplate.findAndModify(Query.query(Criteria.where("_id").is(book.getId())),
                new Update().set("name", book.getName()).set("author", book.getAuthor()).set("genre", book.getGenre()),
                Book.class);
        return book;
    }

    @Override
    public boolean deleteBookById(String id) {
        return mongoTemplate.remove(Query.query(Criteria.where("_id").is(id)), Book.class).getDeletedCount() == 1;
    }

    @Override
    public Optional<Book> findPartialBookById(String id) {
        var aggregation = newAggregation(
                match(Criteria.where("_id").is(id)),
                project().and("_id").as("id")
                        .and("name"));
        return Optional.ofNullable(mongoTemplate.aggregate(aggregation,Book.class,Book.class).getUniqueMappedResult());
    }
}