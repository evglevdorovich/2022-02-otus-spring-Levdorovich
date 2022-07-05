package ru.otus.integration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.otus.integration.runner.ApplicationRunner;

@SpringBootApplication
public class IntegrationApplication {

    public static void main(String[] args) {
        var run = SpringApplication.run(IntegrationApplication.class, args);
        var runner = run.getBean(ApplicationRunner.class);
        runner.run();
    }

}
