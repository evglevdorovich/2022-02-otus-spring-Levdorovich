package ru.otus.testing.service;

import ru.otus.testing.domain.Question;
import ru.otus.testing.dto.ClosedQuestionViewToRightAnswersDTO;
import ru.otus.testing.dto.OpenedQuestionViewToRightAnswerDTO;

public interface QuestionViewService {
    String getViewOfAllQuestions();

    ClosedQuestionViewToRightAnswersDTO getClosedQuestionViewWithAnswersDTO(Question question);

    OpenedQuestionViewToRightAnswerDTO getOpenedQuestionViewWithAnswerDTO(Question question);

}
