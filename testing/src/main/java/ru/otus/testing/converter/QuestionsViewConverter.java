package ru.otus.testing.converter;

import ru.otus.testing.domain.Question;

import java.util.List;

public interface QuestionsViewConverter {
    String getViewQuestions(List<Question> questions);
}
