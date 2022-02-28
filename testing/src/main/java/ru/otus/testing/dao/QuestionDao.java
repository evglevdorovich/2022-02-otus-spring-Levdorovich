package ru.otus.testing.dao;

import ru.otus.testing.domain.Question;

import java.util.List;

public interface QuestionDao {
    List<Question> getAll();
}
