package ru.otus.batch.config;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.JobRepositoryTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.otus.batch.repository.MongoAuthorRepository;
import ru.otus.batch.repository.MongoBookRepository;
import ru.otus.batch.repository.MongoCommentRepository;
import ru.otus.batch.repository.MongoGenreRepository;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@SpringBatchTest
@DisplayName("BookJpaToMongoTest")
class BookJpaToMongoTest {

    private final String BOOK_TO_JPA_MONGO_NAME = "jpaToMongoJob";
    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;
    @Autowired
    private JobRepositoryTestUtils jobRepositoryTestUtils;
    @Autowired
    private MongoAuthorRepository mongoAuthorRepository;
    @Autowired
    private MongoGenreRepository mongoGenreRepository;
    @Autowired
    private MongoBookRepository mongoBookRepository;
    @Autowired
    private MongoCommentRepository mongoCommentRepository;

    @Test
    @DisplayName("correct migrate from jpa to mongo")
    void testJob() throws Exception {
        val job = jobLauncherTestUtils.getJob();
        val jobParameters = new JobParameters(Map.of("uniqueness",
                new JobParameter(String.valueOf((System.nanoTime())))));
        assertThat(job).isNotNull()
                .extracting(Job::getName)
                .isEqualTo(BOOK_TO_JPA_MONGO_NAME);
        JobExecution jobExecution = jobLauncherTestUtils.launchJob(jobParameters);

        assertThat(jobExecution.getExitStatus().getExitCode()).isEqualTo("COMPLETED");

        val books = mongoBookRepository.findAll();
        val comments = mongoCommentRepository.findAll();
        val authors = mongoAuthorRepository.findAll();
        val genres = mongoGenreRepository.findAll();

        assertThat(books).hasSize(2);
        assertThat(authors).hasSize(2);
        assertThat(genres).hasSize(2);
        assertThat(comments).hasSize(3);
    }

}