package ru.otus.testing.services;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.testing.domain.Question;
import ru.otus.testing.domain.TestResult;
import ru.otus.testing.domain.User;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@DisplayName("Class TestServiceImplTest")
@ExtendWith(MockitoExtension.class)
class TestServiceImplTest {
    @Mock
    private QuestionService questionService;
    @Mock
    private UserInfoService userInfoService;
    @Mock
    private QuestionAskingService questionAskingService;
    @Mock
    private TestResultPrinterService testResultPrinterService;
    @InjectMocks
    private TestServiceImpl testService;

    @Test
    @DisplayName("Should execute test")
    void shouldExecuteTest() {
        User user = new User("firstName", "lastName");
        List<Question> questions = new ArrayList<>();
        int quantityOfRightAnsweredQuestions = 3;
        int quantityOfAnsweredQuestions = 5;
        int minScore = 3;
        TestResult testResult = new TestResult(user, quantityOfRightAnsweredQuestions, quantityOfAnsweredQuestions,
                questions, minScore);
        given(userInfoService.askUserInfo())
                .willReturn(user);
        given(questionService.getAll())
                .willReturn(questions);
        given(questionAskingService.askQuestions(questions, user))
                .willReturn(testResult);
        testService.executeTest();
        verify(testResultPrinterService, times(1)).printResult(testResult);
        verifyNoMoreInteractions(questionService, userInfoService, questionAskingService
                , testResultPrinterService);
    }
}