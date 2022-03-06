package ru.otus.testing.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.testing.converter.QuestionsViewConverter;
import ru.otus.testing.domain.Question;
import java.util.ArrayList;
import java.util.List;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;

@DisplayName("Class QuestionViewServiceImplTest")
@ExtendWith(MockitoExtension.class)
class QuestionViewServiceImplTest {
    @Mock
    private QuestionService questionService;
    @Mock
    private QuestionsViewConverter questionsViewConverter;
    private QuestionViewService questionViewService;

    @BeforeEach
    void setUp() {
        questionViewService = new QuestionViewServiceImpl(questionService, questionsViewConverter);
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


}