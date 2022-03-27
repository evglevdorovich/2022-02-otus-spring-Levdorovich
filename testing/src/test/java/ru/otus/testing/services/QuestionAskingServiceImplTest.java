package ru.otus.testing.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.PropertySource;
import ru.otus.testing.converter.QuestionViewConverter;
import ru.otus.testing.domain.Answer;
import ru.otus.testing.domain.Question;
import ru.otus.testing.domain.TestResult;
import ru.otus.testing.domain.User;
import ru.otus.testing.exceptions.InvalidStringOfCommaSeparatedIntegers;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

@DisplayName("class QuestionAskingServiceImplTest:")
@ExtendWith(MockitoExtension.class)
@PropertySource("classpath:application.properties")
class QuestionAskingServiceImplTest {
    @Mock
    private MessageIOService messageIOService;
    @Mock
    private QuestionViewConverter questionViewConverter;
    @Mock
    private AnswersChecker answerChecker;

    private QuestionAskingServiceImpl questionAskingService;
    private User user;
    private List<Question> questions;
    private TestResult expectedTestResult;
    private static final String INSERT_CODE = "insert.pattern";
    private static final String INSERT_LIST_OF_INTEGER_EXCEPTION_WARN = "insert.listOfNumbers.exceptionPattern";

    @BeforeEach
    void setUp() {
        int minimumScore = 3;
        questionAskingService = new QuestionAskingServiceImpl(minimumScore, questionViewConverter,
                answerChecker, messageIOService);
        Question question1 = new Question("desc1", List.of(new Answer("desc1", true)));
        Question question2 = new Question("desc2", List.of(new Answer("desc2", false)
                , new Answer("desc3", true)));

        int quantityOfRightAnswers = 1;
        questions = List.of(question1, question2);
        user = new User("firstName", "lastName");
        expectedTestResult = new TestResult(user, quantityOfRightAnswers, questions.size(),
                questions, minimumScore);

    }

    @Test
    @DisplayName("should return expected TestResult in askQuestions method With 2nd thrown Exception")
    void shouldReturnTestResultInAskQuestionsWithSecondThrownException() {
        String question1View = "desc1";
        String question2View = "desc2";
        String msg = "msg";
        List<Answer> firstAnswers = questions.get(0).getAnswers();
        List<Answer> secondAnswers = questions.get(1).getAnswers();

        lenient().when(questionViewConverter.getViewQuestion(questions.get(0)))
                .thenReturn(question1View);
        lenient().when(questionViewConverter.getViewQuestion(questions.get(1)))
                .thenReturn(question2View);
        InvalidStringOfCommaSeparatedIntegers ex = new InvalidStringOfCommaSeparatedIntegers(msg);

        List<Integer> answeredNumbers = List.of(1);
        when(messageIOService.inputCommaSeparatedIntegersWithPromptAndWarning(INSERT_CODE,
                INSERT_LIST_OF_INTEGER_EXCEPTION_WARN)).thenReturn(answeredNumbers)
                .thenThrow(ex)
                .thenReturn(answeredNumbers);

        lenient().when(answerChecker.checkAnswers(firstAnswers, answeredNumbers))
                .thenReturn(true);
        lenient().when(answerChecker.checkAnswers(secondAnswers, answeredNumbers))
                .thenReturn(false);
        TestResult testResult = questionAskingService.askQuestions(questions, user);

        verify(questionViewConverter, times(1)).getViewQuestion(questions.get(0));
        verify(messageIOService, times(1)).outputText(question1View);
        verify(messageIOService, times(3)).inputCommaSeparatedIntegersWithPromptAndWarning(
                INSERT_CODE, INSERT_LIST_OF_INTEGER_EXCEPTION_WARN);
        verify(messageIOService, times(1)).outputText(ex.getMessage() + "\n");
        verify(answerChecker, times(1)).checkAnswers(questions.get(0).getAnswers(), List.of(1));
        verify(answerChecker, times(1)).checkAnswers(questions.get(1).getAnswers(), List.of(1));

        verify(questionViewConverter, times(2)).getViewQuestion(questions.get(1));
        verify(messageIOService, times(2)).outputText(question2View);

        assertThat(testResult).usingRecursiveComparison().isEqualTo(expectedTestResult);
    }


}