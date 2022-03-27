package ru.otus.testing.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.testing.config.LocaleSettings;
import ru.otus.testing.domain.Question;
import ru.otus.testing.domain.TestResult;
import ru.otus.testing.domain.User;

import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class TestServiceImpl implements TestService {

    private final ChooserLanguage chooserLanguage;
    private final QuestionService questionService;
    private final UserInfoService userInfoService;
    private final QuestionAskingService questionAskingService;
    private final TestResultPrinterService testResultPrinterService;
    private final LocaleSettings localeSettings;

    public void executeTest() {
        Locale selectedLocale = chooserLanguage.chooseLanguage();
        localeSettings.setLocale(selectedLocale);
        User user = userInfoService.askUserInfo();
        List<Question> questions = questionService.getAll();
        TestResult testResult = questionAskingService.askQuestions(questions, user);
        testResultPrinterService.printResult(testResult);
    }

}
