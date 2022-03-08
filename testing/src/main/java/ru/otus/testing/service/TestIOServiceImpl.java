package ru.otus.testing.service;

import org.springframework.stereotype.Service;

import java.io.PrintStream;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class TestIOServiceImpl implements TestIOService {

    private final Scanner scanner;
    private final PrintStream output;
    private static final String CLOSED_ANSWER_PATTERN = "(\\d+,?)*";
    private final Pattern pattern;

    public TestIOServiceImpl() {
        this.output = System.out;
        this.scanner = new Scanner(System.in);
        this.pattern = Pattern.compile(CLOSED_ANSWER_PATTERN);
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
    public String inputAnswersToClosedQuestion() {
        String answer = scanner.next();
        Matcher matcher = pattern.matcher(answer);
        while (!matcher.matches()) {
            outputText("Incorrect answer pattern, please try more");
            answer = scanner.next();
            matcher = pattern.matcher(answer);
        }
        return answer;
    }

}
