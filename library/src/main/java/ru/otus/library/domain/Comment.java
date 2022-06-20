package ru.otus.library.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
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

    @Field(name = "book")
    private ShortBook book;

    public Comment(ShortBook book, String text) {
        this.book = book;
        this.text = text;
    }
}
