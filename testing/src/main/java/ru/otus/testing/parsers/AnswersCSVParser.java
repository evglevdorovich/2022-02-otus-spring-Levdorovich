package ru.otus.testing.parsers;

import ru.otus.testing.domain.Answer;

import java.util.List;

public interface AnswersCSVParser {
    List<Answer> parseCSVStringsToAnswers(String[] answersCSV);
}
