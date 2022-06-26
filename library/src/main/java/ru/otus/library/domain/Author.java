package ru.otus.library.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Document("authors")
public class Author {
    @Id
    private String id;

    @Field(name = "name")
    @Indexed(unique = true)
    private String name;

    public Author(String name) {
        this.name = name;
    }
}
