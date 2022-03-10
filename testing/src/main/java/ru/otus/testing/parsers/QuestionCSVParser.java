package ru.otus.testing.parsers;

import org.springframework.stereotype.Component;
import ru.otus.testing.domain.Answer;
import ru.otus.testing.domain.Question;

import java.util.ArrayList;
import java.util.List;

@Component
public class QuestionCSVParser implements QuestionParser {
    private static final int OPENED_ANSWER_LENGTH = 2;
    private static final int QUESTION_DESCRIPTION_POSITION = 0;
    private static final int OPENED_ANSWER_POSITION = 1;
    private static final int FIRST_ANSWER_POSITION = 1;

    @Override
    public List<Question> parse(List<String[]> lines) {
        List<Question> questions = new ArrayList<>();

        for (String[] nextLine : lines) {
            if (nextLine.length == OPENED_ANSWER_LENGTH) {
                addOpenedQuestion(questions, nextLine);
            } else {
                addClosedQuestion(questions, nextLine);
            }
        }
        return questions;
    }

    private void addClosedQuestion(List<Question> questions, String[] nextLine) {
        List<Answer> answers = getAnswersForClosedQuestion(nextLine);
        questions.add(new Question(nextLine[QUESTION_DESCRIPTION_POSITION], answers));
    }

    private List<Answer> getAnswersForClosedQuestion(String[] nextLine) {
        List<Answer> answers = new ArrayList<>();
        for (int i = FIRST_ANSWER_POSITION; i < nextLine.length; i += 2) {
            answers.add(getClosedAnswer(nextLine, i));
        }
        return answers;
    }

    private Answer getClosedAnswer(String[] nextLine, int i) {
        return new Answer(nextLine[i], Boolean.parseBoolean(nextLine[i + 1]));
    }

    private void addOpenedQuestion(List<Question> questions, String[] nextLine) {
        List<Answer> answers = getOpenedAnswerFromLine(nextLine[OPENED_ANSWER_POSITION]);
        questions.add(new Question(nextLine[QUESTION_DESCRIPTION_POSITION], answers));
    }

    private List<Answer> getOpenedAnswerFromLine(String description) {
        return new ArrayList<>(List.of(new Answer(description, true)));
    }
}
