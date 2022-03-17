package ru.otus.testing.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.testing.converter.QuestionViewConverter;
import ru.otus.testing.domain.Answer;
import ru.otus.testing.domain.Question;
import ru.otus.testing.dto.ClosedQuestionViewToRightAnswersDTO;
import ru.otus.testing.dto.OpenedQuestionViewToRightAnswerDTO;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;

@DisplayName("Class QuestionViewServiceImpl")
@ExtendWith(MockitoExtension.class)
class QuestionViewServiceImplTest {
//    @Mock
//    private QuestionService questionService;
//    @Mock
//    private QuestionViewConverter questionViewConverter;
//    @Mock
//    private AnswerService answerService;
//    private QuestionViewService questionViewService;
//
//    @BeforeEach
//    void setUp() {
//        questionViewService = new QuestionViewServiceImpl(questionService, questionViewConverter, answerService);
//    }
//
//    @DisplayName("should call questionViewConverter getViewQuestions() and question Service getAll()")
//    @Test
//    void shouldCallQuestionViewConverterGetViewAndQuestionServiceGetAll() {
//        String str = "some";
//        List<Question> questions = new ArrayList<>(List.of(new Question("Question", new ArrayList<>())));
//        given(questionService.getAll())
//                .willReturn(questions);
//        given(questionViewConverter.getViewQuestions(questions))
//                .willReturn(str);
//        assertThat(questionViewService.getViewOfAllQuestions()).isEqualTo(str);
//    }
//
//    @DisplayName("should return closedQuestionQuestionToAnswerDTO")
//    @Test
//    void shouldReturnClosedQuestionToAnswerDTO() {
//        String questionDescription = "Question description";
//        String answerDescription1 = "Answer description 1";
//        String answerDescription2 = "Answer description 2";
//        String answerDescription3 = "Answer description 3";
//        List<Integer> rightAnswers = List.of(1, 3);
//        ClosedQuestionViewToRightAnswersDTO closedQuestionToAnswerDTO =
//                new ClosedQuestionViewToRightAnswersDTO(questionDescription,rightAnswers);
//        Question question = new Question(questionDescription, List.of(new Answer(answerDescription1, true),
//                new Answer(answerDescription2, false), new Answer(answerDescription3, true)));
//        given(questionViewConverter.getViewQuestion(question))
//                .willReturn(questionDescription);
//        given(answerService.getRightAnswersNumber(question.getAnswers()))
//                .willReturn(rightAnswers);
//
//        assertThat(questionViewService.getClosedQuestionViewWithAnswersDTO(question)).isEqualTo(closedQuestionToAnswerDTO);
//
//    }
//
//    @DisplayName("should return openedQuestionQuestionToAnswerDTO")
//    @Test
//    void shouldReturnOpenedQuestionToAnswerDTO() {
//        String questionDescription = "Question description";
//        String answerDescription = "Some Answer";
//        Answer answer = new Answer(answerDescription,true);
//        OpenedQuestionViewToRightAnswerDTO openedQuestionViewToRightAnswerDTO =
//                new OpenedQuestionViewToRightAnswerDTO(questionDescription,answerDescription);
//        Question question = new Question(questionDescription, List.of(answer));
//        given(questionViewConverter.getOpenedViewQuestion(question))
//                .willReturn(questionDescription);
//        given(answerService.getRightAnswer(answer))
//                .willReturn(answerDescription);
//        assertThat(questionViewService.getOpenedQuestionViewWithAnswerDTO(question))
//                .isEqualTo(openedQuestionViewToRightAnswerDTO);
//
//    }

}