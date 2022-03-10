package ru.otus.testing.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.testing.domain.Answer;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

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


}