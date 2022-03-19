package ru.otus.testing.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.testing.dao.QuestionDao;
import ru.otus.testing.domain.Question;

import java.util.List;

@RequiredArgsConstructor
@Service
public class QuestionServiceImpl implements QuestionService {
    private final QuestionDao questionDao;

    @Override
    public List<Question> getAll() {
        return questionDao.getAll();
    }

}
