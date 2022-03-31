package ru.otus.testing.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import ru.otus.testing.config.LocaleSettings;
import ru.otus.testing.domain.User;
import ru.otus.testing.services.ChooserLanguage;
import ru.otus.testing.services.TestService;
import ru.otus.testing.services.UserInfoService;

import java.util.Locale;
import java.util.Optional;

@ShellComponent
@RequiredArgsConstructor
public class ApplicationCommands {
    private Optional<User> userOptional = Optional.empty();
    private static final String DEFAULT_FIRST_NAME = "user";
    private static final String DEFAULT_LAST_NAME = "user";
    private static final String NOT_AUTHORIZED_PATTERN = "you haven't login yet";
    private static final String NOT_LOCALIZED_PATTERN = "you haven't choose the language yet";

    private final TestService testService;
    private final ChooserLanguage chooserLanguage;
    private final LocaleSettings localeSettings;
    private final UserInfoService userInfoService;
    private boolean selectedLocale;

    @ShellMethod(value = "Set test language", key = "sl")
    public void setLocale() {
        Locale currentLocale = chooserLanguage.chooseLanguage();
        localeSettings.setLocale(currentLocale);
        selectedLocale = true;
    }

    @ShellMethodAvailability("isLoginAvailable")
    @ShellMethod(value = "login", key = {"l", "login"})
    public void login() {
        userOptional = Optional.of(userInfoService.askUserInfo());
    }

    @ShellMethodAvailability("isExecutingTestAvailable")
    @ShellMethod(value = "execute test", key = {"ex", "execute"})
    public void executeTest() {
        testService.executeTest(userOptional.orElseGet(() -> new User(DEFAULT_FIRST_NAME, DEFAULT_LAST_NAME)));
    }

    public Availability isLoginAvailable() {
        return selectedLocale
                ? Availability.available()
                : Availability.unavailable(NOT_LOCALIZED_PATTERN);
    }

    public Availability isExecutingTestAvailable() {
        return userOptional.isEmpty() ? Availability.unavailable(NOT_AUTHORIZED_PATTERN) : Availability.available();
    }
}
