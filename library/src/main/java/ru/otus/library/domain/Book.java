package ru.otus.library.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@AllArgsConstructor
@Data
public class Book {
    private long id;
    private final String name;
    private final long genreId;
    private final long authorId;
}
