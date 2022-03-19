package ru.otus.testing.services;

import java.util.List;

public interface IOService {

    String inputText();

    void outputText(String text);

    void outputTextInFormat(String format, Object... args);

    List<Integer> inputCommaSeparatedIntegersWithPrompt(String prompt);
}
