package ru.otus.testing.service;

import org.springframework.stereotype.Service;

import java.io.PrintStream;
import java.util.Scanner;

@Service
public class IOServiceImpl implements IOService {

    private final Scanner scanner;
    private final PrintStream output;

    public IOServiceImpl() {
        this.output = System.out;
        this.scanner = new Scanner(System.in);
    }

    @Override
    public void outputText(String text) {
        output.println(text);
    }

    @Override
    public String inputText() {
        return scanner.next();
    }

    @Override
    public String inputAnswersWithPrompt(String prompt) {
        outputText(prompt);
        return scanner.next();
    }

}
