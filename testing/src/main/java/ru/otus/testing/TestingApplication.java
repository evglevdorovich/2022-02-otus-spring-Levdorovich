package ru.otus.testing;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.otus.testing.service.TestService;


public class TestingApplication {
    private static final String APPLICATION_CONTEXT_RELATIVE_PATH = "/spring-context.xml";

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(APPLICATION_CONTEXT_RELATIVE_PATH);
        TestService testService = (TestService) context.getBean("testService");
        testService.startTest();
    }

}
