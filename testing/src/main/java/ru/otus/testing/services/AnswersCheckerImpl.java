package ru.otus.testing.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.testing.domain.Answer;
import ru.otus.testing.helpers.ListComparer;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class AnswersCheckerImpl implements AnswersChecker {
    private final ListComparer listComparer;

    @Override
    public boolean checkAnswers(List<Answer> questionAnswers, List<Integer> numberedAnswers) {
        List<Integer> getRightAnswerNumbers = getRightAnswersNumber(questionAnswers);
        return listComparer.listEqualsIgnoreOrder(getRightAnswerNumbers, numberedAnswers);
    }

    private List<Integer> getRightAnswersNumber(List<Answer> answers) {
        List<Integer> numberOfRightAnswers = new ArrayList<>();
        for (int i = 1; i <= answers.size(); i++) {
            if (answers.get(i - 1).isCorrect())
                numberOfRightAnswers.add(i);
        }
        return numberOfRightAnswers;
    }
}
