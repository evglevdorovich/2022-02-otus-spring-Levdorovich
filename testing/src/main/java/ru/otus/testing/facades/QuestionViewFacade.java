package ru.otus.testing.facades;

import ru.otus.testing.domain.Question;
import ru.otus.testing.dto.ClosedQuestionViewToRightAnswersDTO;
import ru.otus.testing.dto.OpenedQuestionViewToRightAnswerDTO;

import java.util.List;

public interface QuestionViewFacade {
    ClosedQuestionViewToRightAnswersDTO getClosedQuestionViewWithAnswersDTO(Question question);

    OpenedQuestionViewToRightAnswerDTO getOpenedQuestionViewWithAnswerDTO(Question question);

    List<Question> getAll();
}
