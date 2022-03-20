package ru.otus.testing.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.testing.config.LocaleSettings;
import ru.otus.testing.domain.User;

@Component
@RequiredArgsConstructor
public class UserInfoServiceImpl implements UserInfoService {

    private final MessageService messageService;
    private final LocaleSettings localeSettings;
    private final IOService ioService;
    private static final String ENTER_FIRST_NAME_CODE = "enter.firstName";
    private static final String ENTER_LAST_NAME_CODE = "enter.lastName";

    @Override
    public User askUserInfo() {
        printMessage(ENTER_FIRST_NAME_CODE);
        String firstName = ioService.inputText();
        printMessage(ENTER_LAST_NAME_CODE);
        String lastName = ioService.inputText();
        return new User(firstName, lastName);
    }

    void printMessage(String code) {
        ioService.outputText(messageService.getMessage(code, localeSettings.getLocale()));
    }
}
