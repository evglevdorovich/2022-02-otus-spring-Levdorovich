package ru.otus.testing.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.testing.domain.Answer;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.assertj.core.api.BDDAssertions.then;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Class AnswerServiceCSV")
@ExtendWith(MockitoExtension.class)
class AnswerServiceImplTest {

    private AnswerServiceImpl answerServiceImpl;
    private static final String CLOSED_ANSWER_PATTERN = "(\\d+,?)*";
    private Pattern pattern;

    @BeforeEach
    void setUp() {
        answerServiceImpl = new AnswerServiceImpl();
        pattern = Pattern.compile(CLOSED_ANSWER_PATTERN);
    }

    @DisplayName("getRightAnswersNumber should return right answers numbers")
    @Test
    void shouldReturnRightAnswersNumber() {
        List<Answer> answers = new ArrayList<>();
        answers.add(new Answer("desc1", true));
        answers.add(new Answer("desc2", false));
        answers.add(new Answer("desc3", true));
        answers.add(new Answer("desc4", false));
        assertEquals(List.of(1, 3), answerServiceImpl.getRightAnswersNumber(answers));
    }

    @DisplayName("getRightAnswer should return Answer Description")
    @Test
    void shouldReturnAnswerDescription() {
        String desc = "description";
        Answer answer = new Answer(desc, true);
        assertEquals(answerServiceImpl.getRightAnswer(answer), desc);
    }

    @DisplayName("Should match pattern for closed answers")
    @Test
    void shouldMatchattern() {
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