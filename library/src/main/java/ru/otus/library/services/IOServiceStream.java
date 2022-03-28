package ru.otus.library.services;

import org.springframework.stereotype.Service;

import java.io.PrintStream;
import java.util.Scanner;

@Service
public class IOServiceStream implements IOService {
    private final PrintStream out;
    private final Scanner scanner;

    public IOServiceStream() {
        this.out = System.out;
        this.scanner = new Scanner(System.in);
    }

    @Override
    public String inputText() {
        return scanner.next();
    }

    @Override
    public void outputText(String text) {
        out.println(text);
    }
}
