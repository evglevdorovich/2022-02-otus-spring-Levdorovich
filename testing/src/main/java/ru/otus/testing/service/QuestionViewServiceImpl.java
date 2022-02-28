package ru.otus.testing.service;

import lombok.RequiredArgsConstructor;
import ru.otus.testing.converter.QuestionsViewConverter;

@RequiredArgsConstructor
public class QuestionViewServiceImpl implements QuestionViewService {
    private final QuestionService questionService;
    private final QuestionsViewConverter questionsViewConverter;

    @Override
    public String getViewOfAllQuestions() {
        return questionsViewConverter.getViewQuestions(questionService.getAll());
    }
}
