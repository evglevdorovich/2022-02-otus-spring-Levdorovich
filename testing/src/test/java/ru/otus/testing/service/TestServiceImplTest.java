package ru.otus.testing.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import ru.otus.testing.helper.ListEquals;
import ru.otus.testing.parser.StringToIntegerNumberParser;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@DisplayName("Class TestServiceImpl")
@ExtendWith(MockitoExtension.class)
@ComponentScan("src/main/java")
class TestServiceImplTest {
    @Mock
    private QuestionViewService questionViewService;
    private TestServiceImpl testService;
    @Mock
    private TestIOService testIOService;
    @Mock
    private QuestionService questionService;
    @Mock
    private ListEquals listEquals;
    @Mock
    private StringToIntegerNumberParser numberParser;
    @Value("${questions.minScore}")
    private int minimumScore;
    private static final String ENTER_FIRST_NAME = "please enter your first name:";
    private static final String ENTER_SECOND_NAME = "please enter your lastName name:";


    @BeforeEach
    void setUp() {
        testService = new TestServiceImpl(questionViewService, testIOService, questionService, listEquals,
                numberParser, minimumScore);
    }

    @DisplayName("shouldCallInputOutputQuestionServiceGetAll")
    @Test
    void shouldCallInputOutputTextQuestionServiceGetAllAndAnswerQuestions() {
        testService.startTest();
        verify(testIOService, times(1)).outputText(ENTER_FIRST_NAME);
        verify(testIOService, times(2)).inputText();
        verify(testIOService, times(1)).outputText(ENTER_SECOND_NAME);
        verify(questionService, times(1)).getAll();
    }

}