package ru.otus.testing.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.assertj.core.api.BDDAssertions.then;

@DisplayName("Class TestIOServiceImpl")
@ExtendWith(MockitoExtension.class)
class TestIOServiceImplTest {

    private static final String CLOSED_ANSWER_PATTERN = "(\\d+,?)*";
    private Pattern pattern;

    @BeforeEach
    void setUp() {
        pattern = Pattern.compile(CLOSED_ANSWER_PATTERN);
    }

    @DisplayName("Should match pattern for closed answers")
    @Test
    void shouldMatchPattern() {
        String rightPattern1 = "1,2,3";
        String rightPattern2 = "2,3";
        String rightPattern3 = "1";
        String rightPattern4 = "1,";

        Matcher matcher = pattern.matcher(rightPattern1);
        then(matcher.matches()).isEqualTo(true);
        matcher = pattern.matcher(rightPattern2);
        then(matcher.matches()).isEqualTo(true);
        matcher = pattern.matcher(rightPattern3);
        then(matcher.matches()).isEqualTo(true);
        matcher = pattern.matcher(rightPattern4);
        then(matcher.matches()).isEqualTo(true);
    }

    @DisplayName("Shouldn't match pattern for closed answers")
    @Test
    void shouldNotMatchPattern() {
        String rightPattern1 = "1,abc";
        String rightPattern2 = "abc";
        String rightPattern3 = "abc,";

        Matcher matcher = pattern.matcher(rightPattern1);
        then(matcher.matches()).isEqualTo(false);
        matcher = pattern.matcher(rightPattern2);
        then(matcher.matches()).isEqualTo(false);
        matcher = pattern.matcher(rightPattern3);
        then(matcher.matches()).isEqualTo(false);
    }
}