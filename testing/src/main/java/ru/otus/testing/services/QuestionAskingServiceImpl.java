package ru.otus.testing.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.otus.testing.converter.QuestionViewConverter;
import ru.otus.testing.domain.Question;
import ru.otus.testing.domain.TestResult;
import ru.otus.testing.domain.User;
import ru.otus.testing.exceptions.InvalidStringOfCommaSeparatedIntegers;

import java.util.List;

@Component
public class QuestionAskingServiceImpl implements QuestionAskingService {
    private final int minimumScore;
    private final IOService ioService;
    private final QuestionViewConverter questionViewConverter;
    private final AnswersChecker answerChecker;

    private static final String INSERT_PATTERN = "Insert numbers of right answers separated by commas: ";

    public QuestionAskingServiceImpl(@Value("${questions.minScore}") int minimumScore, IOService ioService,
                                     QuestionViewConverter questionViewConverter, AnswersChecker answerChecker) {
        this.minimumScore = minimumScore;
        this.ioService = ioService;
        this.questionViewConverter = questionViewConverter;
        this.answerChecker = answerChecker;
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
            answersList = ioService.inputCommaSeparatedIntegersWithPrompt(INSERT_PATTERN);
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
}
