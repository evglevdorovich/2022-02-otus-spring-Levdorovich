package ru.otus.library.domain;

import lombok.*;
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

    public MongoBook(String name, MongoAuthor author, MongoGenre genre) {
        this.name = name;
        this.author = author;
        this.genre = genre;
    }
}

