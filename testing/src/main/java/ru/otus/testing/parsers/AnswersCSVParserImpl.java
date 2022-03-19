package ru.otus.testing.parsers;

import org.springframework.stereotype.Component;
import ru.otus.testing.domain.Answer;

import java.util.ArrayList;
import java.util.List;

@Component
public class AnswersCSVParserImpl implements AnswersCSVParser{
    private static final int FIRST_ANSWER_POSITION = 1;

    @Override
    public List<Answer> parseCSVStringsToAnswers(String[] answersCSV) {
        List<Answer> answers = new ArrayList<>();
        for (int i = FIRST_ANSWER_POSITION; i < answersCSV.length; i += 2) {
            answers.add(parseAnswer(answersCSV, i));
        }
        return answers;
    }
    private Answer parseAnswer(String[] answersCSV, int i) {
        return new Answer(answersCSV[i], Boolean.parseBoolean(answersCSV[i + 1]));
    }
}
