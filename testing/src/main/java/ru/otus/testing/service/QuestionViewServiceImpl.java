package ru.otus.testing.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.testing.converter.QuestionsViewConverter;
import ru.otus.testing.domain.Question;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class QuestionViewServiceImpl implements QuestionViewService {
    private final QuestionService questionService;
    private final QuestionsViewConverter questionsViewConverter;
    private final AnswerService answerService;
    private static final byte MAP_INITIAL_CAPACITY = 1;
    private static final byte OPENED_ANSWER_INDEX = 0;

    @Override
    public String getViewOfAllQuestions() {
        return questionsViewConverter.getViewQuestions(questionService.getAll());
    }

    @Override
    public Map<String, List<Integer>> getClosedQuestionViewWithAnswers(Question question) {
        Map<String, List<Integer>> questionToAnswers = new HashMap<>(MAP_INITIAL_CAPACITY);
        String closedViewQuestion = questionsViewConverter.getClosedViewQuestion(question);
        List<Integer> rightAnswersNumber = answerService.getRightAnswersNumber(question.getAnswers());
        questionToAnswers.put(closedViewQuestion, rightAnswersNumber);

        return questionToAnswers;
    }

    @Override
    public Map<String, String> getOpenedQuestionViewWithAnswer(Question question) {
        Map<String, String> openedQuestionViewWithAnswer = new HashMap<>();
        String openedViewQuestion = questionsViewConverter.getOpenedViewQuestion(question);
        String rightAnswer = answerService.getRightAnswer(question.getAnswers().get(OPENED_ANSWER_INDEX));
        openedQuestionViewWithAnswer.put(openedViewQuestion, rightAnswer);

        return openedQuestionViewWithAnswer;
    }
}
