package ru.otus.testing.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.testing.dao.QuestionDao;
import ru.otus.testing.domain.Question;
import java.util.ArrayList;
import java.util.List;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;

@DisplayName("Class QuestionServiceCSVTest")
@ExtendWith(MockitoExtension.class)
class QuestionServiceCSVTest {
    @Mock
    private QuestionDao questionDao;
    private QuestionService questionService;

    @BeforeEach
    void setUp() {
        questionService = new QuestionServiceCSV(questionDao);
    }

    @DisplayName("call getAll() from QuestionDao")
    @Test
    void shouldCallPersonDaoGetAll() {
        List<Question> questions = new ArrayList<>(List.of(new Question("Question", new ArrayList<>())));
        given(questionDao.getAll())
                .willReturn(questions);
        assertThat(questionService.getAll()).isEqualTo(questions);
    }
}