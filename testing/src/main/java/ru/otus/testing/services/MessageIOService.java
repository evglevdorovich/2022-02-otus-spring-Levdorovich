package ru.otus.testing.services;

import java.util.List;

public interface MessageIOService {
    void outputMessageByCode(String code, Object... args);

    List<Integer> inputCommaSeparatedIntegersWithPromptAndWarning(String prompt, String warning);

    String inputText();

    void outputText(String text);
}
