package ru.otus.testing.service;

import ru.otus.testing.domain.Answer;

import java.util.List;

public interface AnswerService {
    List<Integer> getRightAnswersNumber(List<Answer> answers);

    String getRightAnswer(Answer answer);
}
