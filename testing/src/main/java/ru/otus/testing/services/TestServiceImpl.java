package ru.otus.testing.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.otus.testing.domain.Question;
import ru.otus.testing.dto.ClosedQuestionViewToRightAnswersDTO;
import ru.otus.testing.dto.OpenedQuestionViewToRightAnswerDTO;
import ru.otus.testing.facades.TestFacade;
import ru.otus.testing.helpers.ListComparer;
import ru.otus.testing.parsers.StringToIntegerNumberParser;

import java.util.List;

@Service
public class TestServiceImpl implements TestService {
    private final ListComparer listComparer;
    private final StringToIntegerNumberParser numberParser;
    private final int minimumScore;
    private final TestFacade testFacade;

    private static final String INCORRECT_CLOSED_ANSWER_PATTERN = "Incorrect answer pattern, please try more";
    private static final String ENTER_FIRST_NAME = "please enter your first name:";
    private static final String ENTER_SECOND_NAME = "please enter your lastName name:";
    private static final String INSERT_PATTERN = "Insert right answers separated by commas: ";
    private static final String PASSED_TEST_PATTERN = "You've passed the test\nYour score is ";
    private static final String NOT_PASSED_TEST_PATTERN = "You haven't passed the test\nYour score is ";

    public TestServiceImpl(ListComparer listComparer, StringToIntegerNumberParser numberParser
            , @Value("${questions.minScore}") int minimumScore, TestFacade testFacade) {
        this.listComparer = listComparer;
        this.numberParser = numberParser;
        this.minimumScore = minimumScore;
        this.testFacade = testFacade;
    }

    public void startTest() {
        testFacade.outputText(ENTER_FIRST_NAME);
        String firstName = testFacade.inputText();
        testFacade.outputText(ENTER_SECOND_NAME);
        String lastName = testFacade.inputText();
        List<Question> questions = testFacade.getAll();
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
        ClosedQuestionViewToRightAnswersDTO closedQuestionViewWithAnswers = testFacade
                .getClosedQuestionViewWithAnswersDTO(question);
        testFacade.outputText(closedQuestionViewWithAnswers.getClosedQuestionView() + "\n" + INSERT_PATTERN);
        String inputString = getValidInputStringAnswerToClosedQuestion();
        List<Integer> numbers = numberParser.stringToListInteger(inputString);
        return listComparer.listEqualsIgnoreOrder(numbers, closedQuestionViewWithAnswers.getNumberOfRightAnswers());
    }

    private String getValidInputStringAnswerToClosedQuestion() {
        String inputString = testFacade.inputText();
        boolean validatedInputString = testFacade.validateInputAnswersToClosedQuestions(inputString);
        while (!validatedInputString) {

            inputString = testFacade.inputAnswersWithPrompt(INCORRECT_CLOSED_ANSWER_PATTERN);
            validatedInputString = testFacade.validateInputAnswersToClosedQuestions(inputString);
        }
        return inputString;
    }

    private boolean answerOpenedQuestion(Question question) {
        OpenedQuestionViewToRightAnswerDTO openedQuestionViewWithAnswer = testFacade
                .getOpenedQuestionViewWithAnswerDTO(question);
        testFacade.outputText(openedQuestionViewWithAnswer.getQuestionView());
        String chosenAnswer = testFacade.inputText().trim();
        return chosenAnswer.equalsIgnoreCase(openedQuestionViewWithAnswer.getAnswer());
    }

    private boolean isOpened(Question question) {
        return question.getAnswers().size() == 1;
    }

    private boolean isClosed(Question question) {
        return question.getAnswers().size() > 1;
    }

    private void printResult(int numberOfCorrectAnswers, String firstName, String lastName) {
        testFacade.outputText("Dear " + firstName + " " + lastName);
        if (minimumScore <= numberOfCorrectAnswers) {
            testFacade.outputText(PASSED_TEST_PATTERN + numberOfCorrectAnswers);
        } else {
            testFacade.outputText(NOT_PASSED_TEST_PATTERN + numberOfCorrectAnswers);
        }
    }


}
