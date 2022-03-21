package ru.otus.testing.services;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.testing.config.LocaleSettings;
import ru.otus.testing.domain.User;

import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@DisplayName("Class UserInfoServiceImplTest")
@ExtendWith(MockitoExtension.class)
class UserInfoServiceImplTest {
    @Mock
    private MessageService messageService;
    @Mock
    private LocaleSettings localeSettings;
    @Mock
    private IOService ioService;
    private static final String ENTER_FIRST_NAME_CODE = "enter.firstName";
    private static final String ENTER_LAST_NAME_CODE = "enter.lastName";
    @InjectMocks
    private UserInfoServiceImpl userInfoService;

    @Test
    @DisplayName("Should return expected User")
    void shouldReturnExpectedUser() {
        String expectedName = "name";
        Locale locale = Locale.ROOT;
        String msg = "msg";
        String msg2 = "msg2";
        User user = new User(expectedName, expectedName);
        when(localeSettings.getLocale())
                .thenReturn(locale);
        when(messageService.getMessage(ENTER_FIRST_NAME_CODE, locale))
                .thenReturn(msg);
        when(messageService.getMessage(ENTER_LAST_NAME_CODE, locale))
                .thenReturn(msg2);
        when(ioService.inputText()).thenReturn(expectedName);
        userInfoService.askUserInfo();
        verify(ioService, times(1)).outputText(msg);
        verify(ioService, times(1)).outputText(msg2);
        assertThat(userInfoService.askUserInfo()).usingRecursiveComparison().isEqualTo(user);
    }

}