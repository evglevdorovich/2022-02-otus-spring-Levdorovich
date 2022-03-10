package ru.otus.testing.service;

public interface IOService {
    void outputText(String text);

    String inputText();

    String inputAnswersWithPrompt(String prompt);
}
