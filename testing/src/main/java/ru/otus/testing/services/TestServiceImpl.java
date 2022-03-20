package ru.otus.testing.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.testing.domain.Question;
import ru.otus.testing.domain.TestResult;
import ru.otus.testing.domain.User;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TestServiceImpl implements TestService {

    private final ChooserLanguage chooserLanguage;
    private final QuestionService questionService;
    private final UserInfoService userInfoService;
    private final QuestionAskingService questionAskingService;
    private final TestResultPrinterService testResultPrinterService;

    public void executeTest() {
        chooserLanguage.chooseLanguage();
        User user = userInfoService.askUserInfo();
        List<Question> questions = questionService.getAll();
        TestResult testResult = questionAskingService.askQuestions(questions, user);
        testResultPrinterService.printResult(testResult);
    }

}
