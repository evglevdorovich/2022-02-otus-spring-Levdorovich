package ru.otus.testing.services;

import org.springframework.stereotype.Service;

import java.io.PrintStream;
import java.util.Scanner;

@Service
public class IOServiceStream implements IOService {

    private final Scanner scanner;
    private final PrintStream output;

    public IOServiceStream() {
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