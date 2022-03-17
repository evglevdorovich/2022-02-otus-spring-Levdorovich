package ru.otus.testing.parsers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.testing.domain.Answer;
import ru.otus.testing.domain.Question;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class QuestionCSVParser implements QuestionParser {
    private static final int QUESTION_DESCRIPTION_POSITION = 0;
    private final AnswersCSVParser answersCSVParser;

    @Override
    public List<Question> parse(List<String[]> lines) {
        List<Question> questions = new ArrayList<>();
        for (String[] nextLine : lines) {
            addQuestion(questions, nextLine);
        }
        return questions;
    }

    private void addQuestion(List<Question> questions, String[] nextLine) {
        List<Answer> answers = answersCSVParser.parseCSVStringsToAnswers(nextLine);
        questions.add(new Question(getDescriptionFromLine(nextLine), answers));
    }

    private String getDescriptionFromLine(String[] nextLine) {
        return nextLine[QUESTION_DESCRIPTION_POSITION];
    }
}
