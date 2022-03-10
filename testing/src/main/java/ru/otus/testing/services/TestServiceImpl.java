package ru.otus.testing.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.otus.testing.domain.Question;
import ru.otus.testing.dto.ClosedQuestionViewToRightAnswersDTO;
import ru.otus.testing.dto.OpenedQuestionViewToRightAnswerDTO;
import ru.otus.testing.helper.ListComparer;
import ru.otus.testing.parser.StringToIntegerNumberParser;

import java.util.List;

@Service
public class TestServiceImpl implements TestService {
    private final QuestionViewService questionViewService;
    private final IOService IOService;
    private final QuestionService questionService;
    private final ListComparer listComparer;
    private final StringToIntegerNumberParser numberParser;
    private final int minimumScore;
    private final AnswerService answerService;

    private static final String INCORRECT_CLOSED_ANSWER_PATTERN = "Incorrect answer pattern, please try more";
    private static final String ENTER_FIRST_NAME = "please enter your first name:";
    private static final String ENTER_SECOND_NAME = "please enter your lastName name:";
    private static final String INSERT_PATTERN = "Insert right answers separated by commas: ";
    private static final String PASSED_TEST_PATTERN = "You've passed the test\nYour score is ";
    private static final String NOT_PASSED_TEST_PATTERN = "You haven't passed the test\nYour score is ";

    public TestServiceImpl(QuestionViewService questionViewService, IOService IOService, AnswerService answerService
            , QuestionService questionService, ListComparer listComparer, StringToIntegerNumberParser numberParser
            , @Value("${questions.minScore}") int minimumScore) {
        this.questionViewService = questionViewService;
        this.IOService = IOService;
        this.questionService = questionService;
        this.listComparer = listComparer;
        this.numberParser = numberParser;
        this.minimumScore = minimumScore;
        this.answerService = answerService;
    }

    public void startTest() {
        IOService.outputText(ENTER_FIRST_NAME);
        String firstName = IOService.inputText();
        IOService.outputText(ENTER_SECOND_NAME);
        String lastName = IOService.inputText();
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
        ClosedQuestionViewToRightAnswersDTO closedQuestionViewWithAnswers = questionViewService
                .getClosedQuestionViewWithAnswersDTO(question);
        IOService.outputText(closedQuestionViewWithAnswers.getClosedQuestionView() + "\n" + INSERT_PATTERN);
        String inputString = getValidInputStringAnswerToClosedQuestion();
        List<Integer> numbers = numberParser.stringToListInteger(inputString);
        return listComparer.listEqualsIgnoreOrder(numbers, closedQuestionViewWithAnswers.getNumberOfRightAnswers());
    }

    private String getValidInputStringAnswerToClosedQuestion() {
        String inputString = IOService.inputText();
        boolean validatedInputString = answerService.validateInputAnswersToClosedQuestions(inputString);
        while (!validatedInputString) {

            inputString = IOService.inputAnswersWithPrompt(INCORRECT_CLOSED_ANSWER_PATTERN);
            validatedInputString = answerService.validateInputAnswersToClosedQuestions(inputString);
        }
        return inputString;
    }

    private boolean answerOpenedQuestion(Question question) {
        OpenedQuestionViewToRightAnswerDTO openedQuestionViewWithAnswer = questionViewService
                .getOpenedQuestionViewWithAnswerDTO(question);
        IOService.outputText(openedQuestionViewWithAnswer.getQuestionView());
        String chosenAnswer = IOService.inputText().trim();
        return chosenAnswer.equalsIgnoreCase(openedQuestionViewWithAnswer.getAnswer());
    }

    private boolean isOpened(Question question) {
        return question.getAnswers().size() == 1;
    }

    private boolean isClosed(Question question) {
        return question.getAnswers().size() > 1;
    }

    private void printResult(int numberOfCorrectAnswers, String firstName, String lastName) {
        IOService.outputText("Dear " + firstName + " " + lastName);
        if (minimumScore <= numberOfCorrectAnswers) {
            IOService.outputText(PASSED_TEST_PATTERN + numberOfCorrectAnswers);
        } else {
            IOService.outputText(NOT_PASSED_TEST_PATTERN + numberOfCorrectAnswers);
        }
    }


}
