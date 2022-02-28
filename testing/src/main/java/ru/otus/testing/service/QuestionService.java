package ru.otus.testing.service;

import ru.otus.testing.domain.Question;

import java.util.List;

public interface QuestionService {
    List<Question> getAll();
}
