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
public class Book {
    @Id
    private String id;

    @Field(name = "name")
    private String name;

    @ToString.Exclude
    private Author author;

    @ToString.Exclude
    private Genre genre;

    public Book(String name, Author author, Genre genre) {
        this.name = name;
        this.author = author;
        this.genre = genre;
    }
}

