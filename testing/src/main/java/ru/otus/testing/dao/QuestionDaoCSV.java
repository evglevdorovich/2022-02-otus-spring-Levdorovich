package ru.otus.testing.dao;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import lombok.RequiredArgsConstructor;
import ru.otus.testing.domain.Question;
import ru.otus.testing.parser.QuestionParser;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
public class QuestionDaoCSV implements QuestionDao {

    private final String RESOURCE_PATH;
    private final QuestionParser questionParser;

    @Override
    public List<Question> getAll() {
        try (CSVReader reader = new CSVReader(
                new BufferedReader
                        (new InputStreamReader(Objects.requireNonNull(getClass().getClassLoader()
                                .getResourceAsStream(RESOURCE_PATH)))))) {
            return questionParser.parse(reader.readAll());
        } catch (IOException | CsvException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
}
