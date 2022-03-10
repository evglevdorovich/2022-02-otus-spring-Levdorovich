package ru.otus.testing.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.testing.converter.QuestionsViewConverter;
import ru.otus.testing.domain.Question;
import ru.otus.testing.dto.ClosedQuestionViewToRightAnswersDTO;
import ru.otus.testing.dto.OpenedQuestionViewToRightAnswerDTO;

import java.util.List;

@RequiredArgsConstructor
@Service
public class QuestionViewServiceImpl implements QuestionViewService {
    private final QuestionService questionService;
    private final QuestionsViewConverter questionsViewConverter;
    private final AnswerService answerService;
    private static final byte OPENED_ANSWER_INDEX = 0;

    @Override
    public String getViewOfAllQuestions() {
        return questionsViewConverter.getViewQuestions(questionService.getAll());
    }

    @Override
    public ClosedQuestionViewToRightAnswersDTO getClosedQuestionViewWithAnswersDTO(Question question) {
        List<Integer> rightAnswersNumber = answerService.getRightAnswersNumber(question.getAnswers());
        String closedViewQuestion = questionsViewConverter.getClosedViewQuestion(question);
        return new ClosedQuestionViewToRightAnswersDTO(closedViewQuestion, rightAnswersNumber);
    }

    @Override
    public OpenedQuestionViewToRightAnswerDTO getOpenedQuestionViewWithAnswerDTO(Question question) {
        String openedViewQuestion = questionsViewConverter.getOpenedViewQuestion(question);
        String rightAnswer = answerService.getRightAnswer(question.getAnswers().get(OPENED_ANSWER_INDEX));
        return new OpenedQuestionViewToRightAnswerDTO(openedViewQuestion, rightAnswer);
    }
}
