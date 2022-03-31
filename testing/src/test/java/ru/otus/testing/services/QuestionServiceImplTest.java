package ru.otus.testing.services;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.testing.dao.QuestionDao;
import ru.otus.testing.domain.Question;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;

@DisplayName("Class QuestionServiceImpl")
@SpringBootTest
class QuestionServiceImplTest {
    @MockBean
    private QuestionDao questionDao;
    @Autowired
    private QuestionService questionService;

    @DisplayName("call getAll() from QuestionDao")
    @Test
    void shouldCallPersonDaoGetAll() {
        List<Question> expectedQuestions = new ArrayList<>(List.of(new Question("Question", new ArrayList<>())));
        given(questionDao.getAll())
                .willReturn(expectedQuestions);
        List<Question> actualQuestions = questionService.getAll();
        assertThat(actualQuestions).isEqualTo(expectedQuestions);
    }
}