package ru.otus.testing.facades;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.testing.domain.Question;
import ru.otus.testing.dto.ClosedQuestionViewToRightAnswersDTO;
import ru.otus.testing.dto.OpenedQuestionViewToRightAnswerDTO;
import ru.otus.testing.helpers.InputAnswerValidator;
import ru.otus.testing.services.IOService;
import ru.otus.testing.services.QuestionService;
import ru.otus.testing.services.QuestionViewService;

import java.util.List;

@Component
@RequiredArgsConstructor
public class TestFacade implements QuestionViewFacade, IOService, InputAnswerValidator {

    private final QuestionViewService questionViewService;
    private final QuestionService questionService;
    private final IOService ioService;
    private final InputAnswerValidator inputAnswerValidator;

    @Override
    public ClosedQuestionViewToRightAnswersDTO getClosedQuestionViewWithAnswersDTO(Question question) {
        return questionViewService.getClosedQuestionViewWithAnswersDTO(question);
    }

    @Override
    public OpenedQuestionViewToRightAnswerDTO getOpenedQuestionViewWithAnswerDTO(Question question) {
        return questionViewService.getOpenedQuestionViewWithAnswerDTO(question);
    }

    @Override
    public List<Question> getAll() {
        return questionService.getAll();
    }

    @Override
    public void outputText(String text) {
        ioService.outputText(text);
    }

    @Override
    public String inputText() {
        return ioService.inputText();
    }

    @Override
    public String inputAnswersWithPrompt(String prompt) {
        return ioService.inputAnswersWithPrompt(prompt);
    }

    @Override
    public boolean validateInputAnswersToClosedQuestions(String answer) {
        return inputAnswerValidator.validateInputAnswersToClosedQuestions(answer);
    }
}
