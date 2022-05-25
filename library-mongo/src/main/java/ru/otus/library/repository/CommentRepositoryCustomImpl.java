package ru.otus.library.repository;

import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import ru.otus.library.exceptions.InvalidDataForUpdateException;
import ru.otus.library.model.Book;
import ru.otus.library.model.Comment;

import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

@RequiredArgsConstructor
public class CommentRepositoryCustomImpl implements CommentRepositoryCustom {
    private final MongoTemplate mongoTemplate;

    @Override
    public Comment saveComment(Comment comment) {
        var bookId = comment.getBook().getId();
        comment.setId(new ObjectId().toHexString());
        var update = new Update().addToSet("comments", comment);
        mongoTemplate.updateFirst(Query.query(Criteria.where("_id").is(bookId)),
                update, "books");
        return comment;
    }

    @Override
    public Comment updateComment(Comment comment) {
        var bookId = comment.getBook().getId();
        var commentId = comment.getId();
        var modifiedCount = mongoTemplate.updateFirst(Query.query(Criteria.where("_id").is(bookId)
                        .and("comments").elemMatch(Criteria.where("_id").is(commentId))),
                Update.update("comments.$.text", comment.getText()), Book.class).getModifiedCount();
        if (modifiedCount == 0) {
            throw new InvalidDataForUpdateException();
        }
        return comment;
    }

    @Override
    public List<Comment> findByBookId(String bookId) {
        var aggregation = newAggregation(
                match(Criteria.where("_id").is(bookId)),
                unwind("comments"),
                project().and("_id").as("book._id").and("comments._id").as("_id")
                        .and("comments.text").as("text").and("name").as("book.name"));
        return mongoTemplate.aggregate(aggregation, Book.class, Comment.class).getMappedResults();
    }

    @Override
    public boolean deleteByBookAndCommentId(String bookId, String commentId) {
        return mongoTemplate.updateFirst(Query.query(Criteria.where("_id").is(bookId)),
                        new Update().pull("comments", Query.query(Criteria.where("_id").is(commentId))), Book.class)
                .getModifiedCount() == 1;
    }

}
