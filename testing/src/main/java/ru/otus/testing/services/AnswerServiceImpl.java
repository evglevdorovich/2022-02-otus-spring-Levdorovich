package ru.otus.testing.services;

import org.springframework.stereotype.Service;
import ru.otus.testing.domain.Answer;

import java.util.ArrayList;
import java.util.List;

@Service
public class AnswerServiceImpl implements AnswerService {

    public AnswerServiceImpl() {

    }

    @Override
    public List<Integer> getRightAnswersNumber(List<Answer> answers) {
        List<Integer> numberOfRightAnswers = new ArrayList<>();
        for (int i = 1; i <= answers.size(); i++) {
            if (answers.get(i - 1).isCorrect())
                numberOfRightAnswers.add(i);
        }
        return numberOfRightAnswers;
    }

    @Override
    public String getRightAnswer(Answer answer) {
        return answer.getDescription();
    }

}
