package ru.otus.testing.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import ru.otus.testing.helpers.ListComparer;
import ru.otus.testing.parsers.StringToIntegerNumberParser;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@DisplayName("Class TestServiceImpl")
@ExtendWith(MockitoExtension.class)
@ComponentScan("src/main/java")
class TestServiceImplTest {
//    private TestServiceImpl testService;
//    @Mock
//    private ListComparer listComparer;
//    @Mock
//    private StringToIntegerNumberParser numberParser;
//    @Mock
//    private TestFacade testFacade;
//    @Mock
//    private UserInfoService userInfoService;
//    @Value("${questions.minScore}")
//    private int minimumScore;
//    private static final String ENTER_FIRST_NAME = "please enter your first name:";
//    private static final String ENTER_SECOND_NAME = "please enter your lastName name:";
//
//
//    @BeforeEach
//    void setUp() {
//        testService = new TestServiceImpl(listComparer,numberParser,minimumScore,testFacade, userInfoService);
//    }
//
//    @DisplayName("shouldCallInputOutputQuestionServiceGetAll")
//    @Test
//    void shouldCallInputOutputTextQuestionServiceGetAllAndAnswerQuestions() {
//        testService.executeTest();
////        verify(testFacade, times(1)).outputText(ENTER_FIRST_NAME);
////        verify(testFacade, times(2)).inputText();
////        verify(testFacade, times(1)).outputText(ENTER_SECOND_NAME);
////        verify(testFacade, times(1)).getAll();
//    }

}