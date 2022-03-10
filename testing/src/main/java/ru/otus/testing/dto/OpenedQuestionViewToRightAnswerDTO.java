package ru.otus.testing.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OpenedQuestionViewToRightAnswerDTO {
    private String questionView;
    private String answer;
}
