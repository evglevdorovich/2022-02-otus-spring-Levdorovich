package ru.otus.library.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BookForUpdateDto {
    private String name;
    private long genreId;
    private long authorId;
}
