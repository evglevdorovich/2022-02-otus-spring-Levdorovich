package ru.otus.testing.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.testing.config.LocaleSettings;
import ru.otus.testing.domain.TestResult;

@Component
@RequiredArgsConstructor
public class TestResultPrinterServiceImpl implements TestResultPrinterService {
    private final IOService ioService;
    private final MessageService messageService;
    private final LocaleSettings localeSettings;
    private static final String FORMAT_RESULT_CODE = "test.result";
    private static final String SUCCESS_RESULT_CODE = "test.result.success";
    private static final String FAIL_RESULT_CODE = "test.result.fail";

    @Override
    public void printResult(TestResult testResult) {
        String userName = testResult.getUser().getFirstName();
        String lastName = testResult.getUser().getLastName();
        int quantityOfAnsweredQuestions = testResult.getQuantityOfAnsweredQuestions();
        int quantityOfRightAnsweredQuestions = testResult.getQuantityOfRightAnsweredQuestions();
        int minResult = testResult.getMinScore();
        printMessage(FORMAT_RESULT_CODE, userName, lastName, quantityOfRightAnsweredQuestions,
                quantityOfAnsweredQuestions);
        printMessage(quantityOfRightAnsweredQuestions >= minResult ? SUCCESS_RESULT_CODE :
                FAIL_RESULT_CODE);
    }

    void printMessage(String code, Object... args) {
        ioService.outputText(messageService.getMessage(code, args, localeSettings.getLocale()));
    }
}
