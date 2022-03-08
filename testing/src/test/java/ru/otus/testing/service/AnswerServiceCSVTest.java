package ru.otus.testing.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.testing.domain.Answer;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Class AnswerServiceCSV")
@ExtendWith(MockitoExtension.class)
class AnswerServiceCSVTest {

    private AnswerServiceCSV answerServiceCSV;

    @BeforeEach
    void setUp() {
        answerServiceCSV = new AnswerServiceCSV();
    }

    @DisplayName("getRightAnswersNumber should return right answers numbers")
    @Test
    void shouldReturnRightAnswersNumber() {
        List<Answer> answers = new ArrayList<>();
        answers.add(new Answer("desc1", true));
        answers.add(new Answer("desc2", false));
        answers.add(new Answer("desc3", true));
        answers.add(new Answer("desc4", false));
        assertEquals(List.of(1, 3), answerServiceCSV.getRightAnswersNumber(answers));
    }

    @DisplayName("getRightAnswer should return Answer Description")
    @Test
    void shouldReturnAnswerDescription() {
        String desc = "description";
        Answer answer = new Answer(desc, true);
        assertEquals(answerServiceCSV.getRightAnswer(answer), desc);
    }
}