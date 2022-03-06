package ru.otus.testing.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayName("Class TestServiceImplTest")
@ExtendWith(MockitoExtension.class)
class TestServiceImplTest {
    @Mock
    private QuestionViewService questionViewService;
    private TestService testService;

    @BeforeEach
    void setUp() {
        testService = new TestServiceImpl(questionViewService);
    }

}