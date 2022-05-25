package ru.otus.library.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Comment {
    @Id
    private String id;

    @Field(name = "text")
    private String text;

    @ToString.Exclude
    private Book book;

    public Comment(Book book, String text) {
        this.book = book;
        this.text = text;
    }
}
