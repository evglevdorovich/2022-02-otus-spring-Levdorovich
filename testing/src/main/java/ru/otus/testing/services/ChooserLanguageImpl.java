package ru.otus.testing.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.testing.config.LocaleSettings;

import java.util.Locale;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class ChooserLanguageImpl implements ChooserLanguage {
    private final LocaleSettings localeSettings;
    private final MessageIOService messageIOService;

    private static final String LANG_CHOOSER_CODE = "lang.choose";
    private static final String LANG_INCORRECT_NUMBER = "lang.warn.incorrectNumber";
    private static final String INPUT_NUMBER_PATTERN = "\\d+";

    @Override
    public Locale chooseLanguage() {
        Map<String, String> languageToLocaleTag = localeSettings.getLanguageToLocaleTag();
        Map<Integer, String> orderToLanguage = localeSettings.getOrderToLanguage();
        Integer input = getSelectedNumberForLanguage(orderToLanguage);
        String language = orderToLanguage.get(input);
        return Locale.forLanguageTag(languageToLocaleTag.get(language));
    }

    private Integer getSelectedNumberForLanguage(Map<Integer, String> orderToLanguage) {
        int sizeOfMap = orderToLanguage.size();
        String stringChoosePattern = buildStringToChoosePattern(orderToLanguage);
        messageIOService.outputMessageByCode(LANG_CHOOSER_CODE);
        messageIOService.outputText(stringChoosePattern);
        String input = messageIOService.inputText();
        if (!isValidNumberForSizeOfMap(sizeOfMap, input)) {
            return getSelectedNumberForLanguage(orderToLanguage);
        } else {
            return Integer.parseInt(input);
        }
    }

    private boolean isValidNumberForSizeOfMap(int sizeOfMap, String input) {
        if (isNumber(input)) {
            int number = Integer.parseInt(input);
            return isCorrespondsToNumbersInMap(sizeOfMap, number);
        } else {
            messageIOService.outputMessageByCode(LANG_INCORRECT_NUMBER);
            return false;
        }
    }

    private boolean isCorrespondsToNumbersInMap(int sizeOfMap, int number) {
        return number >= 0 && number <= sizeOfMap;
    }

    private boolean isNumber(String input) {
        return input.matches(INPUT_NUMBER_PATTERN);
    }

    private String buildStringToChoosePattern(Map<Integer, String> orderToLanguage) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<Integer, String> orderToLanguageEntry : orderToLanguage.entrySet()) {
            sb.append(orderToLanguageEntry.getKey())
                    .append(" - ")
                    .append(orderToLanguageEntry.getValue())
                    .append(", ");
        }
        sb.deleteCharAt(sb.lastIndexOf(",")).trimToSize();
        return sb.toString();
    }
}
