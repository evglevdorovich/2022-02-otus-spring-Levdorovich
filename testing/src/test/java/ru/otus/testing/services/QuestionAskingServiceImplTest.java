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

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.lenient;

@DisplayName("class QuestionAskingServiceImplTest:")
@ExtendWith(MockitoExtension.class)
@PropertySource("classpath:application.properties")
class QuestionAskingServiceImplTest {
//    @Mock
//    private IOService ioService;
//    @Mock
//    private QuestionViewConverter questionViewConverter;
//    @Mock
//    private AnswersChecker answerChecker;
//    private QuestionAskingServiceImpl questionAskingService;
//    private User user;
//    private List<Question> questions;
//    private TestResult expectedTestResult;
//    private static final String INSERT_PATTERN = "Insert numbers of right answers separated by commas: ";
//
//    @BeforeEach
//    void setUp() {
//        int minimumScore = 3;
//        questionAskingService = new QuestionAskingServiceImpl(minimumScore, ioService, questionViewConverter,
//                answerChecker, localeSettings);
//        Question question1 = new Question("desc1", List.of(new Answer("desc1", true)));
//        Question question2 = new Question("desc2", List.of(new Answer("desc2", false)
//                , new Answer("desc3", true)));
//
//        int quantityOfRightAnswers = 1;
//        questions = List.of(question1, question2);
//        user = new User("firstName", "lastName");
//        expectedTestResult = new TestResult(user, quantityOfRightAnswers, questions.size(),
//                questions, minimumScore);
//
//    }
//
//    @Test
//    @DisplayName("should return expected TestResult in askQuestions method")
//    void shouldReturnTestResultInAskQuestions() {
//        List<Answer> firstAnswers = questions.get(0).getAnswers();
//        List<Answer> secondAnswers = questions.get(1).getAnswers();
//        List<Integer> answerNumbers = List.of(1);
//        lenient().when(ioService.inputCommaSeparatedIntegersWithPrompt(INSERT_PATTERN))
//                .thenReturn(answerNumbers);
//        lenient().when(answerChecker.checkAnswers(firstAnswers, answerNumbers))
//                .thenReturn(true);
//        lenient().when(answerChecker.checkAnswers(secondAnswers, answerNumbers))
//                .thenReturn(false);
//        TestResult testResult = questionAskingService.askQuestions(questions, user);
//        assertThat(testResult).usingRecursiveComparison().isEqualTo(expectedTestResult);
//    }
//

}