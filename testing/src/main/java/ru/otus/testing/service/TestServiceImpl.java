package ru.otus.testing.service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TestServiceImpl implements TestService {
    private final QuestionViewService questionViewService;

    public void startTest() {
        System.out.println(questionViewService.getViewOfAllQuestions());
    }
}
