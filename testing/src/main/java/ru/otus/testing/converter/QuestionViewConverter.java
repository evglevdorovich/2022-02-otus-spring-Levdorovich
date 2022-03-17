package ru.otus.testing.converter;

import ru.otus.testing.domain.Question;

public interface QuestionViewConverter {
    String getViewQuestion(Question question);
}
