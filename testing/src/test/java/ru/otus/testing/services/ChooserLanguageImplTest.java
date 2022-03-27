package ru.otus.testing.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.testing.config.LocaleSettings;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@DisplayName("class ChooserLanguageImplTest:")
@ExtendWith(MockitoExtension.class)
class ChooserLanguageImplTest {

    private static final String LANG_CHOOSER_CODE = "lang.choose";
    private static final String LANG_INCORRECT_NUMBER = "lang.warn.incorrectNumber";
    @Mock
    private LocaleSettings localeSettings;
    @Mock
    private MessageIOService messageIOService;
    @InjectMocks
    private ChooserLanguageImpl chooserLanguage;

    private Map<String, String> languageForLocaleTag;
    private Map<Integer, String> orderToLanguage;
    private String textPattern;
    private String invalidInputStringForExpectedNumber;
    private String validInputStringForExpectedNumber;
    private String ruTag;

    @BeforeEach
    void setUp() {
        ruTag = "ru";
        languageForLocaleTag = new HashMap<>();
        languageForLocaleTag.put("English", "");
        languageForLocaleTag.put("Russian", "ru");
        orderToLanguage = new HashMap<>();
        orderToLanguage.put(1, "English");
        orderToLanguage.put(2, "Russian");
        textPattern = "1 - English, 2 - Russian ";
        invalidInputStringForExpectedNumber = "2f";
        validInputStringForExpectedNumber = "2";
    }

    @Test
    @DisplayName("test chooseLanguage should change Locale with first incorrectInput")
    void shouldChangeLocaleWithFirstIncorrectNumber() {
        when(localeSettings.getLanguageToLocaleTag())
                .thenReturn(languageForLocaleTag);
        when(localeSettings.getOrderToLanguage())
                .thenReturn(orderToLanguage);
        when(messageIOService.inputText())
                .thenReturn(invalidInputStringForExpectedNumber).thenReturn(validInputStringForExpectedNumber);

        chooserLanguage.chooseLanguage();

        verify(messageIOService, times(2)).outputMessageByCode(LANG_CHOOSER_CODE);
        verify(messageIOService, times(2)).outputText(textPattern);
        verify(messageIOService, times(2)).inputText();
        verify(messageIOService, times(1)).outputMessageByCode(LANG_INCORRECT_NUMBER);
        assertThat(chooserLanguage.chooseLanguage()).isEqualTo(Locale.forLanguageTag(ruTag));
    }
}