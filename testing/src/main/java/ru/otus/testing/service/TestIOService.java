package ru.otus.testing.service;

public interface TestIOService {
    void outputText(String text);

    String inputText();

    String inputAnswersToClosedQuestion();
}
