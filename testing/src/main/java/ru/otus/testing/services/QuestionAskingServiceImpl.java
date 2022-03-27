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
    private final MessageIOService messageIOService;
    private final QuestionViewConverter questionViewConverter;
    private final AnswersChecker answerChecker;

    private static final String INSERT_CODE = "insert.pattern";
    private static final String INSERT_LIST_OF_INTEGER_EXCEPTION_WARN = "insert.listOfNumbers.exceptionPattern";

    public QuestionAskingServiceImpl(@Value("${questions.minScore}") int minimumScore,
                                     QuestionViewConverter questionViewConverter, AnswersChecker answerChecker,
                                     MessageIOService messageIOService) {
        this.minimumScore = minimumScore;
        this.questionViewConverter = questionViewConverter;
        this.answerChecker = answerChecker;
        this.messageIOService = messageIOService;
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
            answersList = messageIOService.inputCommaSeparatedIntegersWithPromptAndWarning(INSERT_CODE,
                    INSERT_LIST_OF_INTEGER_EXCEPTION_WARN);
        } catch (InvalidStringOfCommaSeparatedIntegers ex) {
            messageIOService.outputText(ex.getMessage() + "\n");
            return getIntegerListWithAnswers(question);
        }
        return answersList;
    }

    private void printQuestionWithPrompt(Question question) {
        String questionView = questionViewConverter.getViewQuestion(question);
        messageIOService.outputText(questionView);
    }

}
