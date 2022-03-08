package ru.otus.testing;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import ru.otus.testing.service.TestService;
import ru.otus.testing.service.TestServiceImpl;

@Configuration
@ComponentScan
@PropertySource("classpath:application.properties")
public class TestingApplication {

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(TestingApplication.class);
        TestService testService = context.getBean(TestServiceImpl.class);
        testService.startTest();
    }

}
