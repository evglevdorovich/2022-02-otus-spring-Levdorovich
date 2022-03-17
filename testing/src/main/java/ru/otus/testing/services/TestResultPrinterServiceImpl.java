package ru.otus.testing.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.testing.domain.TestResult;

@Component
@RequiredArgsConstructor
public class TestResultPrinterServiceImpl implements TestResultPrinterService {
    private final IOService ioService;
    private static final String FORMAT_RESULT = "Dear %s %s your result is %d/%d%n";

    @Override
    public void printResult(TestResult testResult) {
        String userName = testResult.getUser().getFirstName();
        String lastName = testResult.getUser().getLastName();
        int quantityOfAnsweredQuestions = testResult.getQuantityOfAnsweredQuestions();
        int quantityOfRightAnsweredQuestions = testResult.getQuantityOfRightAnsweredQuestions();
        int minResult = testResult.getMinScore();
        System.out.printf(FORMAT_RESULT, userName, lastName, quantityOfRightAnsweredQuestions,
                quantityOfAnsweredQuestions);
        ioService.outputText(quantityOfRightAnsweredQuestions >= minResult ? "You've passed the test." :
                "You haven't passed the test.");
    }
}
