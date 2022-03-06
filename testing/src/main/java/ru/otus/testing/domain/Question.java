package ru.otus.testing.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class Question {
    private String description;
    private List<Answer> answers;
}
