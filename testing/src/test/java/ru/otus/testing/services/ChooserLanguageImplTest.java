package ru.otus.testing.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.testing.config.LocaleSettings;

import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@DisplayName("class ChooserLanguageImplTest:")
@SpringBootTest
class ChooserLanguageImplTest {

    private static final String LANG_CHOOSER_CODE = "lang.choose";
    private static final String LANG_INCORRECT_NUMBER = "lang.warn.incorrectNumber";
    private static final String ENGLISH_LANGUAGE = "English";
    private static final String RUSSIAN_LANGUAGE = "Russian";
    @MockBean
    private LocaleSettings localeSettings;
    @MockBean
    private MessageIOService messageIOService;
    @Autowired
    private ChooserLanguage chooserLanguage;

    private List<String> availableLanguage;
    private String textPattern;
    private String invalidInputStringForExpectedNumber;
    private String validInputStringForExpectedNumber;
    private String ruTag;


    @BeforeEach
    void setUp() {
        availableLanguage = new LinkedList<>();
        availableLanguage.add(ENGLISH_LANGUAGE);
        availableLanguage.add(RUSSIAN_LANGUAGE);

        ruTag = "ru";
        textPattern = "1 - English, 2 - Russian ";
        invalidInputStringForExpectedNumber = "2f";
        validInputStringForExpectedNumber = "2";
    }

    @Test
    @DisplayName("test chooseLanguage should change Locale with first incorrectInput")
    void shouldChangeLocaleWithFirstIncorrectNumber() {
        when(localeSettings.getAvailableLanguages())
                .thenReturn(availableLanguage);
        when(messageIOService.inputText())
                .thenReturn(invalidInputStringForExpectedNumber).thenReturn(validInputStringForExpectedNumber);
        when(localeSettings.getLocaleTagByLanguage(RUSSIAN_LANGUAGE))
                .thenReturn(ruTag);

        chooserLanguage.chooseLanguage();

        verify(messageIOService, times(2)).outputMessageByCode(LANG_CHOOSER_CODE);
        verify(messageIOService, times(2)).outputText(textPattern);
        verify(messageIOService, times(2)).inputText();
        verify(messageIOService, times(1)).outputMessageByCode(LANG_INCORRECT_NUMBER);
        assertThat(chooserLanguage.chooseLanguage()).isEqualTo(Locale.forLanguageTag(ruTag));
    }
}