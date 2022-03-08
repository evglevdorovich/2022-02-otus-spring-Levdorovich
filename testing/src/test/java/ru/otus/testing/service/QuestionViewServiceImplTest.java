package ru.otus.testing.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.testing.converter.QuestionsViewConverter;
import ru.otus.testing.domain.Answer;
import ru.otus.testing.domain.Question;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;

@DisplayName("Class QuestionViewServiceImpl")
@ExtendWith(MockitoExtension.class)
class QuestionViewServiceImplTest {
    @Mock
    private QuestionService questionService;
    @Mock
    private QuestionsViewConverter questionsViewConverter;
    @Mock
    private AnswerService answerService;
    private QuestionViewService questionViewService;

    @BeforeEach
    void setUp() {
        questionViewService = new QuestionViewServiceImpl(questionService, questionsViewConverter, answerService);
    }

    @DisplayName("should call questionViewConverter getViewQuestions() and question Service getAll()")
    @Test
    void shouldCallQuestionViewConverterGetViewAndQuestionServiceGetAll() {
        String str = "some";
        List<Question> questions = new ArrayList<>(List.of(new Question("Question", new ArrayList<>())));
        given(questionService.getAll())
                .willReturn(questions);
        given(questionsViewConverter.getViewQuestions(questions))
                .willReturn(str);
        assertThat(questionViewService.getViewOfAllQuestions()).isEqualTo(str);
    }

    @DisplayName("should return closed question to answer map")
    @Test
    void shouldReturnClosedQuestionToAnswerMap() {
        String questionDescription = "Question description";
        String answerDescription1 = "Answer description 1";
        String answerDescription2 = "Answer description 2";
        String answerDescription3 = "Answer description 3";
        Map<String, List<Integer>> questionToAnswerMap = new HashMap<>();
        List<Integer> rightAnswers = List.of(1, 3);
        questionToAnswerMap.put(questionDescription, rightAnswers);

        Question question = new Question(questionDescription, List.of(new Answer(answerDescription1, true),
                new Answer(answerDescription2, false), new Answer(answerDescription3, true)));
        given(questionsViewConverter.getClosedViewQuestion(question))
                .willReturn(questionDescription);
        given(answerService.getRightAnswersNumber(question.getAnswers()))
                .willReturn(rightAnswers);

        assertThat(questionViewService.getClosedQuestionViewWithAnswers(question)).isEqualTo(questionToAnswerMap);

    }


}