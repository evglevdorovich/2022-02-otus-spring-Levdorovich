package ru.otus.testing.services;

import ru.otus.testing.domain.Question;

import java.util.List;

public interface QuestionService {
    List<Question> getAll();
}
