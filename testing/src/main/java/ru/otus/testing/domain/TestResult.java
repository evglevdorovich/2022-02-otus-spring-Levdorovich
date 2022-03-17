package ru.otus.testing.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class TestResult {
    private User user;
    private int quantityOfRightAnsweredQuestions;
    private int quantityOfAnsweredQuestions;
    private List<Question> questions;
    private int minScore;
}
