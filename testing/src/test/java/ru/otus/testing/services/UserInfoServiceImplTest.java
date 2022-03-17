package ru.otus.testing.services;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.testing.domain.User;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@DisplayName("Class UserInfoServiceImplTest")
@ExtendWith(MockitoExtension.class)
class UserInfoServiceImplTest {

    private static final String ENTER_FIRST_NAME_TEMPLATE = "please enter your first name:";
    private static final String ENTER_SECOND_NAME_TEMPLATE = "please enter your lastName name:";
    @Mock
    private IOService ioService;
    @InjectMocks
    private UserInfoServiceImpl userInfoService;

    @Test
    @DisplayName("Should return expected User")
    void shouldReturnExpectedUser() {
        String expectedName = "name";
        User user = new User(expectedName, expectedName);
        given(ioService.inputText()).willReturn(expectedName);
        userInfoService.askUserInfo();
        verify(ioService, times(1)).outputText(ENTER_FIRST_NAME_TEMPLATE);
        verify(ioService, times(1)).outputText(ENTER_SECOND_NAME_TEMPLATE);
        assertThat(userInfoService.askUserInfo()).usingRecursiveComparison().isEqualTo(user);
    }

}