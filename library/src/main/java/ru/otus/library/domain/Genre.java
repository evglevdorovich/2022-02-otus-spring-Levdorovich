package ru.otus.library.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
@Data
public class Genre {
    private long id;
    private String name;

    public Genre(long id) {
        this.id = id;
    }
}
