package ru.otus.testing.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.testing.config.LocaleSettings;

@Component
@RequiredArgsConstructor
public class ChooserLanguageImpl implements ChooserLanguage {
    private final LocaleSettings localeSettings;
    private final IOService ioService;
    private final MessageService messageService;

    private static final String LANG_CHOOSER_CODE = "lang.choose";
    private static final String LANG_INCORRECT_NUMBER = "lang.warn.incorrectNumber";
    private static final String INPUT_NUMBER_PATTERN = "[12]";
    private static final String NUMBER_OF_ROOT_LOCALE = "1";
    private static final String NUMBER_OF_RUSSIAN_LOCALE = "2";

    @Override
    public void chooseLanguage() {
        printMessage(LANG_CHOOSER_CODE);
        String numberOfLanguage = ioService.inputText();
        if (!numberOfLanguage.matches(INPUT_NUMBER_PATTERN)) {
            printMessage(LANG_INCORRECT_NUMBER);
            chooseLanguage();
            return;
        }
        changeLocale(numberOfLanguage);
    }

    private void changeLocale(String number) {
        if (number.equals(NUMBER_OF_ROOT_LOCALE)) {
            localeSettings.changeToRootLocale();
        } else if (number.equals(NUMBER_OF_RUSSIAN_LOCALE)) {
            localeSettings.changeToRuLocale();
        }
    }

    private void printMessage(String code) {
        String msg = messageService.getMessage(code, localeSettings.getLocale());
        ioService.outputText(msg);
    }
}
