package ru.otus.testing.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.testing.config.LocaleSettings;
import ru.otus.testing.domain.Question;
import ru.otus.testing.domain.TestResult;
import ru.otus.testing.domain.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static org.mockito.Mockito.*;

@DisplayName("Class TestResultPrinterServiceImplTest")
@ExtendWith(MockitoExtension.class)
class TestResultPrinterServiceImplTest {
    @InjectMocks
    private TestResultPrinterServiceImpl testResultPrinterService;
    @Mock
    private IOService ioService;
    @Mock
    private MessageService messageService;
    @Mock
    private LocaleSettings localeSettings;

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
    @DisplayName("should print test result")
    void shouldPrintSuccessResult() {
        Locale locale = Locale.ROOT;
        when(localeSettings.getLocale())
                .thenReturn(locale);
        String userName = testResult.getUser().getFirstName();
        String lastName = testResult.getUser().getLastName();
        int quantityOfRightAnsweredQuestions = testResult.getQuantityOfRightAnsweredQuestions();
        int quantityOfAnsweredQuestion = testResult.getQuantityOfAnsweredQuestions();
        String result = "result";
        lenient().when(messageService.getMessage(FORMAT_RESULT_CODE, new Object[]{userName, lastName, quantityOfRightAnsweredQuestions,
                quantityOfAnsweredQuestion},locale))
                .thenReturn(result);
        String success = "success";
        lenient().when(messageService.getMessage(SUCCESS_RESULT_CODE,new Object[]{},locale))
                .thenReturn(success);
        testResultPrinterService.printResult(testResult);
        verify(ioService, times(1)).outputText(result);
        verify(ioService, times(1)).outputText(success);
    }
}