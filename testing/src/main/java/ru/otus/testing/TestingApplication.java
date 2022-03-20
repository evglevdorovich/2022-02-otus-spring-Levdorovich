package ru.otus.testing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import ru.otus.testing.services.TestService;
import ru.otus.testing.services.TestServiceImpl;

@SpringBootApplication
public class TestingApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = SpringApplication.run(TestingApplication.class, args);
        TestService testService = ctx.getBean(TestServiceImpl.class);
        testService.executeTest();

    }

}
