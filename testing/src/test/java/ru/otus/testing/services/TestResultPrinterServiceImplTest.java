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

import static org.mockito.Mockito.*;

@DisplayName("Class TestResultPrinterServiceImplTest")
@ExtendWith(MockitoExtension.class)
class TestResultPrinterServiceImplTest {
    @InjectMocks
    private TestResultPrinterServiceImpl testResultPrinterService;
    @Mock
    private MessageIOService messageIOService;

    private TestResult testResult;
    private static final String FORMAT_RESULT_CODE = "test.result";
    private static final String SUCCESS_RESULT_CODE = "test.result.success";
    private static final String FAIL_RESULT_CODE = "test.result.fail";

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
    @DisplayName("should print test success result")
    void shouldPrintSuccessResult() {
        String userName = testResult.getUser().getFirstName();
        String lastName = testResult.getUser().getLastName();
        int quantityOfRightAnsweredQuestions = testResult.getQuantityOfRightAnsweredQuestions();
        int quantityOfAnsweredQuestion = testResult.getQuantityOfAnsweredQuestions();
        testResultPrinterService.printResult(testResult);
        verify(messageIOService, times(1)).outputMessageByCode(FORMAT_RESULT_CODE, userName,
                lastName, quantityOfRightAnsweredQuestions,
                quantityOfAnsweredQuestion);
        verify(messageIOService, times(1)).outputMessageByCode(SUCCESS_RESULT_CODE);
        verifyNoMoreInteractions(messageIOService);
    }

    @Test
    @DisplayName("should print test fail result")
    void shouldPrintFailResult() {
        String userName = testResult.getUser().getFirstName();
        String lastName = testResult.getUser().getLastName();
        int quantityOfRightAnsweredQuestionsForFail = 2;
        testResult.setQuantityOfRightAnsweredQuestions(quantityOfRightAnsweredQuestionsForFail);
        int quantityOfAnsweredQuestion = testResult.getQuantityOfAnsweredQuestions();
        testResultPrinterService.printResult(testResult);
        verify(messageIOService, times(1)).outputMessageByCode(FORMAT_RESULT_CODE, userName,
                lastName, quantityOfRightAnsweredQuestionsForFail,
                quantityOfAnsweredQuestion);
        verify(messageIOService, times(1)).outputMessageByCode(FAIL_RESULT_CODE);
        verifyNoMoreInteractions(messageIOService);
    }
}