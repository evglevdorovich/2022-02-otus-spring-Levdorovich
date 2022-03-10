package ru.otus.testing.service;

import org.springframework.stereotype.Service;
import ru.otus.testing.domain.Answer;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class AnswerServiceImpl implements AnswerService {

    private static final String CLOSED_ANSWER_PATTERN = "(\\d+,?)*";
    private final Pattern pattern;

    public AnswerServiceImpl() {
        pattern = Pattern.compile(CLOSED_ANSWER_PATTERN);
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

    @Override
    public boolean validateInputAnswersToClosedQuestions(String answer) {
        Matcher matcher = pattern.matcher(answer);
        return matcher.matches();
    }
}
