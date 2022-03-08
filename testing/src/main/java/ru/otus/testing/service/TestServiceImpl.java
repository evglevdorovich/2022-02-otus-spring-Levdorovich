package ru.otus.testing.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.otus.testing.domain.Question;
import ru.otus.testing.helper.ListEquals;
import ru.otus.testing.parser.StringToIntegerNumberParser;

import java.util.List;
import java.util.Map;

@Service
public class TestServiceImpl implements TestService {
    private final QuestionViewService questionViewService;
    private final TestIOService testIOService;
    private final QuestionService questionService;
    private final ListEquals listEquals;
    private final StringToIntegerNumberParser numberParser;
    private final int minimumScore;

    private static final String ENTER_FIRST_NAME = "please enter your first name:";
    private static final String ENTER_SECOND_NAME = "please enter your lastName name:";
    private static final String INSERT_PATTERN = "Insert right answers separated by commas: ";
    private static final String PASSED_TEST_PATTERN = "You've passed the test\nYour score is ";
    private static final String NOT_PASSED_TEST_PATTERN = "You haven't passed the test\nYour score is ";

    public TestServiceImpl(QuestionViewService questionViewService, TestIOService testIOService
            , QuestionService questionService, ListEquals listEquals
            , StringToIntegerNumberParser numberParser, @Value("${questions.minScore}") int minimumScore) {
        this.questionViewService = questionViewService;
        this.testIOService = testIOService;
        this.questionService = questionService;
        this.listEquals = listEquals;
        this.numberParser = numberParser;
        this.minimumScore = minimumScore;
    }

    public void startTest() {
        testIOService.outputText(ENTER_FIRST_NAME);
        String firstName = testIOService.inputText();
        testIOService.outputText(ENTER_SECOND_NAME);
        String lastName = testIOService.inputText();
        List<Question> questions = questionService.getAll();
        int numberOfCorrectAnswers = answerQuestions(questions);
        printResult(numberOfCorrectAnswers, firstName, lastName);
    }

    private int answerQuestions(List<Question> questions) {
        int numberOfCorrectAnswers = 0;

        for (Question question : questions) {
            if (answerQuestion(question)) {
                numberOfCorrectAnswers++;
            }
        }
        return numberOfCorrectAnswers;
    }

    private boolean answerQuestion(Question question) {
        if (isOpened(question)) {
            return answerOpenedQuestion(question);
        } else if (isClosed(question)) {
            return answerClosedQuestion(question);
        } else {
            return false;
        }
    }

    private boolean answerClosedQuestion(Question question) {
        Map<String, List<Integer>> closedQuestionViewWithAnswers = questionViewService
                .getClosedQuestionViewWithAnswers(question);
        int answersSize = question.getAnswers().size();
        for (Map.Entry<String, List<Integer>> q : closedQuestionViewWithAnswers.entrySet()) {
            testIOService.outputText(q.getKey() + "\n" + INSERT_PATTERN);
            List<Integer> numbers = numberParser.StringToListInteger(testIOService.inputAnswersToClosedQuestion());
            return listEquals.listEqualsIgnoreOrder(numbers, q.getValue());
        }
        return false;
    }

    private boolean answerOpenedQuestion(Question question) {
        Map<String, String> openedQuestionViewWithAnswer = questionViewService
                .getOpenedQuestionViewWithAnswer(question);
        for (Map.Entry<String, String> q : openedQuestionViewWithAnswer.entrySet()) {
            testIOService.outputText(q.getKey());
            String chosenAnswer = testIOService.inputText().trim();
            return chosenAnswer.equalsIgnoreCase(q.getValue());
        }
        return false;
    }


    private boolean isOpened(Question question) {
        return question.getAnswers().size() == 1;
    }

    private boolean isClosed(Question question) {
        return question.getAnswers().size() > 1;
    }

    private void printResult(int numberOfCorrectAnswers, String firstName, String lastName) {
        testIOService.outputText("Dear " + firstName + " " + lastName);
        if (minimumScore <= numberOfCorrectAnswers) {
            testIOService.outputText(PASSED_TEST_PATTERN + numberOfCorrectAnswers);
        } else {
            testIOService.outputText(NOT_PASSED_TEST_PATTERN + numberOfCorrectAnswers);
        }
    }


}
