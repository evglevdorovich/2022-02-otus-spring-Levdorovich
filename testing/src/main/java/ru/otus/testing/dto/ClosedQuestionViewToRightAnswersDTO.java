package ru.otus.testing.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ClosedQuestionViewToRightAnswersDTO {
    private String closedQuestionView;
    private List<Integer> numberOfRightAnswers;
}
