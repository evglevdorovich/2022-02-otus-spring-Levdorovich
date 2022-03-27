package ru.otus.testing.services;

import org.springframework.stereotype.Service;
import ru.otus.testing.exceptions.InvalidStringOfCommaSeparatedIntegers;
import ru.otus.testing.parsers.StringToIntegerNumberParser;

import java.io.PrintStream;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class IOServiceStream implements IOService {

    private final Scanner scanner;
    private final PrintStream output;
    private final Pattern pattern;
    private final StringToIntegerNumberParser stringToIntegerNumberParser;
    private static final String CLOSED_ANSWER_PATTERN = "(\\d+,?)*";

    public IOServiceStream(StringToIntegerNumberParser stringToIntegerNumberParser) {
        this.pattern = Pattern.compile(CLOSED_ANSWER_PATTERN);
        this.output = System.out;
        this.scanner = new Scanner(System.in);
        this.stringToIntegerNumberParser = stringToIntegerNumberParser;
    }

    @Override
    public void outputText(String text) {
        output.println(text);
    }

    @Override
    public List<Integer> inputCommaSeparatedIntegersWithPromptAndWarning(String prompt, String warning) {
        outputText(prompt);
        String strIntegers = inputText();
        if (!validateStringOfCommaSeparatedIntegers(strIntegers)) {
            String message = strIntegers + warning;
            throw new InvalidStringOfCommaSeparatedIntegers(message);
        }
        return stringToIntegerNumberParser.stringToListInteger(strIntegers);
    }

    private boolean validateStringOfCommaSeparatedIntegers(String answer) {
        Matcher matcher = pattern.matcher(answer);
        return matcher.matches();
    }

    @Override
    public String inputText() {
        return scanner.next();
    }

}
