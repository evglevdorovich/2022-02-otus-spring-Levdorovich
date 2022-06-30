package ru.otus.batch.model.mongo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Document("books")
public class MongoBook {
    @Id
    private String id;

    @Field(name = "name")
    private String name;

    @ToString.Exclude
    private MongoAuthor author;

    @ToString.Exclude
    private MongoGenre genre;
}
