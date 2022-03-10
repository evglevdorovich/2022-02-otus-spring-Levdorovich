package ru.otus.testing.parser;

import ru.otus.testing.domain.Question;

import java.util.List;

public interface QuestionParser {
    List<Question> parse(List<String[]> lines);
}
