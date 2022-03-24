package ru.otus.testing.services;

import java.util.List;

public interface IOService {

    String inputText();

    void outputText(String text);

    List<Integer> inputCommaSeparatedIntegersWithPromptAndWarning(String prompt, String warning);

}
