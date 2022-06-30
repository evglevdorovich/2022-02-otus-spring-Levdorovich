package ru.otus.batch.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@RequiredArgsConstructor
@ShellComponent
public class BatchCommands {
    private final JobLauncher jobLauncher;
    private final Job jpaToMongoJob;

    @ShellMethod(value = "startMigrationJob", key = "sm")
    public void startMigration() throws Exception {
        jobLauncher.run(jpaToMongoJob, new JobParameters());
    }

}
