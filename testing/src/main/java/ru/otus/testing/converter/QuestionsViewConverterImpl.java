package ru.otus.testing.converter;

import ru.otus.testing.domain.Answer;
import ru.otus.testing.domain.Question;

import java.util.List;

public class QuestionsViewConverterImpl implements QuestionsViewConverter {

    @Override
    public String getViewQuestions(List<Question> questions) {
        StringBuilder sb = new StringBuilder();

        for (Question question : questions) {
            appendQuestionDescriptionToStringBuilder(sb, question.getDescription());
            appendAnswersToStringBuilder(sb, question.getAnswers());
            sb.append("\n");
        }
        return sb.toString();
    }

    private void appendQuestionDescriptionToStringBuilder(StringBuilder sb, String description) {
        sb.append(description).append("\n");
    }

    private void appendAnswersToStringBuilder(StringBuilder sb, List<Answer> answers) {
        for (int i = 0; i < answers.size(); i++) {
            appendQuestionDescriptionToStringBuilder(sb.append(i + 1).append(". "), answers.get(i).getDescription());
        }
    }
}
