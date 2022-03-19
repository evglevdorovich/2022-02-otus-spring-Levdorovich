package ru.otus.testing.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.testing.domain.Answer;
import ru.otus.testing.helpers.ListComparer;

import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@DisplayName("class AnswersCheckerImplTest:")
@ExtendWith(MockitoExtension.class)
class AnswersCheckerImplTest {
    @Mock
    private ListComparer listComparer;
    @InjectMocks
    private AnswersCheckerImpl answersChecker;
    private List<Integer> numberedAnswers;
    private List<Answer> answers;

    @BeforeEach
    void setUp() {
        numberedAnswers = List.of(1, 3);
        Answer answer1 = new Answer("desc1", true);
        Answer answer2 = new Answer("desc2", false);
        Answer answer3 = new Answer("desc3", true);
        answers = List.of(answer1, answer2, answer3);
    }

    @DisplayName("should call equalsIgnoreOrderInCheckAnswers")
    @Test
    void shouldCallEqualsIgnoreOrderInCheckAnswers() {
        answersChecker.checkAnswers(answers, numberedAnswers);
        verify(listComparer, times(1)).listEqualsIgnoreOrder(numberedAnswers, numberedAnswers);
    }


}