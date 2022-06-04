package ru.otus.library.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BookForViewDto {
    private long id;
    private String name;
    private String genreName;
    private String authorName;
}
