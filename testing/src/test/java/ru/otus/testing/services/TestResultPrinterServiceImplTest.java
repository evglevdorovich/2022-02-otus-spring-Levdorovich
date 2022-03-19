package ru.otus.testing.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.testing.domain.Question;
import ru.otus.testing.domain.TestResult;
import ru.otus.testing.domain.User;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@DisplayName("Class TestResultPrinterServiceImplTest")
@ExtendWith(MockitoExtension.class)
class TestResultPrinterServiceImplTest {
    @InjectMocks
    private TestResultPrinterServiceImpl testResultPrinterService;
    @Mock
    private IOService ioService;
    private TestResult testResult;
    private static final String FORMAT_RESULT = "Dear %s %s your result is %d/%d%n";
    private static final String SUCCESS_RESULT = "You've passed the test.";
    private static final String FAIL_RESULT = "You haven't passed the test.";

    @BeforeEach
    void setUp() {
        int quantityOfRightAnsweredQuestions = 3;
        int quantityOfAnsweredQuestion = 5;
        List<Question> questions = new ArrayList<>();
        int minScore = 3;
        testResult = new TestResult(new User("firstName", "lastName"), quantityOfRightAnsweredQuestions,
                quantityOfAnsweredQuestion, questions, minScore);
    }

    @Test
    @DisplayName("should print success result")
    void shouldPrintSuccessResult() {
        testResultPrinterService.printResult(testResult);
        String userName = testResult.getUser().getFirstName();
        String lastName = testResult.getUser().getLastName();
        int quantityOfRightAnsweredQuestions = testResult.getQuantityOfRightAnsweredQuestions();
        int quantityOfAnsweredQuestion = testResult.getQuantityOfAnsweredQuestions();
        verify(ioService, times(1)).outputTextInFormat(FORMAT_RESULT, userName, lastName, quantityOfRightAnsweredQuestions,
                quantityOfAnsweredQuestion);
        verify(ioService, times(1)).outputText(SUCCESS_RESULT);
    }

    @Test
    @DisplayName("should print fail result")
    void shouldPrintFailResult() {
        testResult.setMinScore(4);
        testResultPrinterService.printResult(testResult);
        verify(ioService, times(1)).outputText(FAIL_RESULT);
    }
}