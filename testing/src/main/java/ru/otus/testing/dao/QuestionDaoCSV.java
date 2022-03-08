package ru.otus.testing.dao;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import ru.otus.testing.domain.Question;
import ru.otus.testing.exceptions.CsvValidateException;
import ru.otus.testing.exceptions.EmptyResourceException;
import ru.otus.testing.exceptions.ResourceNotFoundException;
import ru.otus.testing.parser.QuestionParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Objects;


@Repository
public class QuestionDaoCSV implements QuestionDao {

    private final String resourcePath;
    private final QuestionParser questionParser;

    public QuestionDaoCSV(@Value("${questions.resourcePath}") String resourcePath, QuestionParser questionParser) {
        this.resourcePath = resourcePath;
        this.questionParser = questionParser;
    }

    @Override
    public List<Question> getAll() {
        try (CSVReader reader = new CSVReader(
                new BufferedReader
                        (new InputStreamReader(Objects.requireNonNull(getClass().getClassLoader()
                                .getResourceAsStream(resourcePath)))))) {
            List<String[]> csvResource = reader.readAll();
            if (csvResource.isEmpty()) {
                throw new EmptyResourceException(resourcePath);
            }
            return questionParser.parse(csvResource);
        } catch (NullPointerException npe) {
            throw new ResourceNotFoundException("questions.resourcePath", npe);
        } catch (CsvException e) {
            throw new CsvValidateException("csv validate fails", e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
