package ru.otus.testing.services;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.testing.domain.User;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@DisplayName("Class UserInfoServiceImplTest")
@SpringBootTest
class UserInfoServiceImplTest {
    @MockBean
    private MessageIOService messageIOService;
    private static final String ENTER_FIRST_NAME_CODE = "enter.firstName";
    private static final String ENTER_LAST_NAME_CODE = "enter.lastName";
    @Autowired
    private UserInfoService userInfoService;

    @Test
    @DisplayName("Should return expected User")
    void shouldReturnExpectedUser() {
        String expectedName = "name";
        String expectedSurname = "surname";
        User expectedUser = new User(expectedName, expectedSurname);
        when(messageIOService.inputText())
                .thenReturn(expectedName).thenReturn(expectedSurname);
        User user = userInfoService.askUserInfo();
        verify(messageIOService, times(1)).outputMessageByCode(ENTER_FIRST_NAME_CODE);
        verify(messageIOService, times(2)).inputText();
        verify(messageIOService, times(1)).outputMessageByCode(ENTER_LAST_NAME_CODE);
        verifyNoMoreInteractions(messageIOService);
        assertThat(user).usingRecursiveComparison().isEqualTo(expectedUser);
    }

}