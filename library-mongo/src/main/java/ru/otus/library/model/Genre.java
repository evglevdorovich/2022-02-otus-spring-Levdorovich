package ru.otus.library.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Document("genres")
public class Genre {
    @Id
    private String id;

    @Field(name = "name")
    @Indexed(unique = true)
    private String name;

    public Genre(String name) {
        this.name = name;
    }
}
