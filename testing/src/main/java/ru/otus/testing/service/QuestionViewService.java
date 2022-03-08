package ru.otus.testing.service;

import ru.otus.testing.domain.Question;

import java.util.List;
import java.util.Map;

public interface QuestionViewService {
    String getViewOfAllQuestions();

    Map<String, List<Integer>> getClosedQuestionViewWithAnswers(Question question);

    Map<String, String> getOpenedQuestionViewWithAnswer(Question question);
}
