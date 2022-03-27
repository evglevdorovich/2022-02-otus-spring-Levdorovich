package ru.otus.testing.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.testing.domain.TestResult;

@Component
@RequiredArgsConstructor
public class TestResultPrinterServiceImpl implements TestResultPrinterService {
    private final MessageIOService messageIOService;
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
        messageIOService.outputMessageByCode(FORMAT_RESULT_CODE, userName, lastName, quantityOfRightAnsweredQuestions,
                quantityOfAnsweredQuestions);
        messageIOService.outputMessageByCode(quantityOfRightAnsweredQuestions >= minResult ? SUCCESS_RESULT_CODE :
                FAIL_RESULT_CODE);
    }

}
