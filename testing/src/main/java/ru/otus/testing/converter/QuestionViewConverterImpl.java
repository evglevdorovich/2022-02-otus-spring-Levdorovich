package ru.otus.testing.converter;

import org.springframework.stereotype.Component;
import ru.otus.testing.domain.Answer;
import ru.otus.testing.domain.Question;

import java.util.List;

@Component
public class QuestionViewConverterImpl implements QuestionViewConverter {

    @Override
    public String getViewQuestion(Question question) {
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
            String answerDescription = answers.get(i).getDescription();
            sb.append(i + 1).append(". ")
                    .append(answerDescription)
                    .append("\n");
        }
    }
}
