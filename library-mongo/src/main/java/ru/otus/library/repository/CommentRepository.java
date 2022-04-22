package ru.otus.library.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.library.model.Comment;

public interface CommentRepository extends MongoRepository<Comment, String>, CommentRepositoryCustom {

}
