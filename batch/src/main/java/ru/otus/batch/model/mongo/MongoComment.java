package ru.otus.batch.model.mongo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Document("comments")
public class MongoComment {
    @Id
    private String id;

    @Field(name = "text")
    private String text;

    @Field(name = "book")
    private ShortBook book;
}
