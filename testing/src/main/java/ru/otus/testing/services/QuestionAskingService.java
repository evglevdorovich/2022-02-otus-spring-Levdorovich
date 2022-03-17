package ru.otus.testing.services;

import ru.otus.testing.domain.Question;
import ru.otus.testing.domain.TestResult;
import ru.otus.testing.domain.User;

import java.util.List;

public interface QuestionAskingService {

    TestResult askQuestions(List<Question> questions, User user);
}
