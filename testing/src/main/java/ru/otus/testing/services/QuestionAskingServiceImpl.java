package ru.otus.testing.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.otus.testing.config.LocaleSettings;
import ru.otus.testing.converter.QuestionViewConverter;
import ru.otus.testing.domain.Question;
import ru.otus.testing.domain.TestResult;
import ru.otus.testing.domain.User;
import ru.otus.testing.exceptions.InvalidStringOfCommaSeparatedIntegers;

import java.util.List;
import java.util.Locale;

@Component
public class QuestionAskingServiceImpl implements QuestionAskingService {
    private final int minimumScore;
    private final IOService ioService;
    private final QuestionViewConverter questionViewConverter;
    private final AnswersChecker answerChecker;
    private final MessageService messageService;
    private final LocaleSettings localeSettings;

    private static final String INSERT_CODE = "insert.pattern";
    private static final String INSERT_LIST_OF_INTEGER_EXCEPTION_WARN = "insert.listOfNumbers.exceptionPattern";

    public QuestionAskingServiceImpl(@Value("${questions.minScore}") int minimumScore, IOService ioService,
                                     QuestionViewConverter questionViewConverter, AnswersChecker answerChecker,
                                     MessageService messageService, LocaleSettings localeSettings) {
        this.minimumScore = minimumScore;
        this.ioService = ioService;
        this.questionViewConverter = questionViewConverter;
        this.answerChecker = answerChecker;
        this.messageService = messageService;
        this.localeSettings = localeSettings;
    }

    @Override
    public TestResult askQuestions(List<Question> questions, User user) {
        int quantityOfAnsweredQuestion = questions.size();
        int quantityOfRightAnsweredQuestion = 0;
        for (Question question : questions) {
            if (answerQuestion(question)) {
                quantityOfRightAnsweredQuestion++;
            }
        }
        return new TestResult(user, quantityOfRightAnsweredQuestion, quantityOfAnsweredQuestion, questions, minimumScore);
    }

    private boolean answerQuestion(Question question) {
        List<Integer> integerListWithAnswers = getIntegerListWithAnswers(question);
        return answerChecker.checkAnswers(question.getAnswers(), integerListWithAnswers);
    }

    private List<Integer> getIntegerListWithAnswers(Question question) {
        printQuestionWithPrompt(question);
        List<Integer> answersList;
        try {
            String insertString = getMessageFromCurrentLocale(INSERT_CODE);
            String warning = getMessageFromCurrentLocale(INSERT_LIST_OF_INTEGER_EXCEPTION_WARN);
            answersList = ioService.inputCommaSeparatedIntegersWithPromptAndWarning(insertString, warning);
        } catch (InvalidStringOfCommaSeparatedIntegers ex) {
            ioService.outputText(ex.getMessage() + "\n");
            return getIntegerListWithAnswers(question);
        }
        return answersList;
    }

    private void printQuestionWithPrompt(Question question) {
        String questionView = questionViewConverter.getViewQuestion(question);
        ioService.outputText(questionView);
    }

    private String getMessageFromCurrentLocale(String code) {
        Locale currentLocale = localeSettings.getLocale();
        return messageService.getMessage(code, currentLocale);
    }


}
