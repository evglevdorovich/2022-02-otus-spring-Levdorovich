package ru.otus.testing.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Answer {
    private String description;
    private boolean correct;
}
