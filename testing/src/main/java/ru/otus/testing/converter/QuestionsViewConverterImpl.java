package ru.otus.testing.converter;

import org.springframework.stereotype.Component;
import ru.otus.testing.domain.Answer;
import ru.otus.testing.domain.Question;

import java.util.List;

@Component
public class QuestionsViewConverterImpl implements QuestionsViewConverter {

    @Override
    public String getViewQuestions(List<Question> questions) {
        StringBuilder sb = new StringBuilder();

        for (Question question : questions) {
            addQuestionToView(sb, question);
        }
        return sb.toString();
    }

    private void addQuestionToView(StringBuilder sb, Question question) {
        appendQuestionDescriptionToStringBuilder(sb, question.getDescription());
        appendAnswersToStringBuilder(sb, question.getAnswers());
        sb.append("\n");
    }

    @Override
    public String getOpenedViewQuestion(Question question) {
        return question.getDescription();
    }

    @Override
    public String getClosedViewQuestion(Question question) {
        StringBuilder sb = new StringBuilder();
        appendQuestionDescriptionToStringBuilder(sb, question.getDescription());
        appendAnswersToStringBuilder(sb, question.getAnswers());
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
