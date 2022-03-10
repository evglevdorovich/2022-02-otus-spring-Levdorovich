package ru.otus.testing.services;

public interface IOService {
    void outputText(String text);

    String inputText();

    String inputAnswersWithPrompt(String prompt);
}
