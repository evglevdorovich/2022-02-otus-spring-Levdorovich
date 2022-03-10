package ru.otus.testing.helpers;

import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class InputAnswerValidatorImpl implements InputAnswerValidator{

    private static final String CLOSED_ANSWER_PATTERN = "(\\d+,?)*";
    private final Pattern pattern;

    public InputAnswerValidatorImpl() {
        pattern = Pattern.compile(CLOSED_ANSWER_PATTERN);
    }

    public boolean validateInputAnswersToClosedQuestions(String answer) {
        Matcher matcher = pattern.matcher(answer);
        return matcher.matches();
    }
}
