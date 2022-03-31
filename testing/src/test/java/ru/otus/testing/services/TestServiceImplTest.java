package ru.otus.testing.services;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.testing.domain.Question;
import ru.otus.testing.domain.TestResult;
import ru.otus.testing.domain.User;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@DisplayName("Class TestServiceImplTest")
@SpringBootTest
class TestServiceImplTest {
    @MockBean
    private QuestionService questionService;
    @MockBean
    private QuestionAskingService questionAskingService;
    @MockBean
    private TestResultPrinterService testResultPrinterService;
    @Autowired
    private TestService testService;

    @Test
    @DisplayName("Should execute test")
    void shouldExecuteTest() {
        User user = new User("firstName", "lastName");
        List<Question> questions = new ArrayList<>();
        int quantityOfRightAnsweredQuestions = 3;
        int quantityOfAnsweredQuestions = 5;
        int minScore = 3;
        TestResult testResult = new TestResult(user, quantityOfRightAnsweredQuestions, quantityOfAnsweredQuestions,
                questions, minScore);
        given(questionService.getAll())
                .willReturn(questions);
        given(questionAskingService.askQuestions(questions, user))
                .willReturn(testResult);
        testService.executeTest(user);
        verify(questionService).getAll();
        verify(testResultPrinterService, times(1)).printResult(testResult);
    }
}