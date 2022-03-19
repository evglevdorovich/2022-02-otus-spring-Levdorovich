package ru.otus.testing.services;

import ru.otus.testing.domain.Answer;

import java.util.List;

public interface AnswersChecker {
    boolean checkAnswers(List<Answer> questionAnswers, List<Integer> numberedAnswers);
}
