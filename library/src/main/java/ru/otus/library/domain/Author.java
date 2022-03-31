package ru.otus.library.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Author {
    private long id;
    private String name;

    public Author(long id) {
        this.id = id;
    }
}
