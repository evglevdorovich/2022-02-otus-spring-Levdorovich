package ru.otus.testing.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.testing.domain.User;

@Component
@RequiredArgsConstructor
public class UserInfoServiceImpl implements UserInfoService {

    private final MessageIOService messageIOService;
    private static final String ENTER_FIRST_NAME_CODE = "enter.firstName";
    private static final String ENTER_LAST_NAME_CODE = "enter.lastName";

    @Override
    public User askUserInfo() {
        messageIOService.outputMessageByCode(ENTER_FIRST_NAME_CODE);
        String firstName = messageIOService.inputText();
        messageIOService.outputMessageByCode(ENTER_LAST_NAME_CODE);
        String lastName = messageIOService.inputText();
        return new User(firstName, lastName);
    }
}
