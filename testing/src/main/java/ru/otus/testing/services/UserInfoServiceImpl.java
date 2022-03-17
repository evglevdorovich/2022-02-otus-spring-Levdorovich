package ru.otus.testing.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.testing.domain.User;

@Component
@RequiredArgsConstructor
public class UserInfoServiceImpl implements UserInfoService {

    private static final String ENTER_FIRST_NAME = "please enter your first name:";
    private static final String ENTER_SECOND_NAME = "please enter your lastName name:";

    private final IOService ioService;

    @Override
    public User askUserInfo() {
        ioService.outputText(ENTER_FIRST_NAME);
        String firstName = ioService.inputText();
        ioService.outputText(ENTER_SECOND_NAME);
        String lastName = ioService.inputText();
        return new User(firstName, lastName);
    }
}
