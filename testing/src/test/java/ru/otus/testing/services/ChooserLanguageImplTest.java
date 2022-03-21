package ru.otus.testing.services;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.testing.config.LocaleSettings;

import java.util.Locale;

import static org.mockito.Mockito.*;

@DisplayName("class ChooserLanguageImplTest:")
@ExtendWith(MockitoExtension.class)
class ChooserLanguageImplTest {

    private static final String LANG_CHOOSER_CODE = "lang.choose";
    private static final String LANG_INCORRECT_NUMBER = "lang.warn.incorrectNumber";
    private static final String NUMBER_OF_ROOT_LOCALE = "1";
    private static final String NUMBER_OF_RUSSIAN_LOCALE = "2";
    @Mock
    private LocaleSettings localeSettings;
    @Mock
    private IOService ioService;
    @Mock
    private MessageService messageService;
    @InjectMocks
    private ChooserLanguageImpl chooserLanguage;

    @Test
    @DisplayName("test chooseLanguage should change Locale to ROOT with first String: incorrectNumber")
    void shouldChangeToEnLocaleWithFirstIncorrectNumber() {
        Locale localeEx1 = Locale.forLanguageTag("ru");
        String msg = "msg";
        String outputError = "output error";
        String inCorrectNumberOfLanguage = "3";
        when(localeSettings.getLocale())
                .thenReturn(localeEx1);
        when(messageService.getMessage(LANG_CHOOSER_CODE, localeEx1))
                .thenReturn(msg);
        when(messageService.getMessage(LANG_INCORRECT_NUMBER, localeEx1))
                .thenReturn(outputError);
        when(ioService.inputText())
                .thenReturn(inCorrectNumberOfLanguage)
                .thenReturn(NUMBER_OF_ROOT_LOCALE);
        chooserLanguage.chooseLanguage();
        verify(localeSettings, times(3)).getLocale();
        verify(ioService, times(2)).outputText(msg);
        verify(ioService).outputText(outputError);
        verify(localeSettings).changeToRootLocale();

    }

    @Test
    @DisplayName("test chooseLanguage should change Locale to RU with first String: incorrectNumber ")
    void shouldChangeToRuLocaleWithFirstIncorrectNumber(){
        Locale localeEx1 = Locale.ROOT;
        String msg = "msg";
        String outputError = "output error";
        String inCorrectNumberOfLanguage = "3";
        when(localeSettings.getLocale())
                .thenReturn(localeEx1);
        when(messageService.getMessage(LANG_CHOOSER_CODE, localeEx1))
                .thenReturn(msg);
        when(messageService.getMessage(LANG_INCORRECT_NUMBER, localeEx1))
                .thenReturn(outputError);
        when(ioService.inputText())
                .thenReturn(inCorrectNumberOfLanguage)
                .thenReturn(NUMBER_OF_RUSSIAN_LOCALE);
        chooserLanguage.chooseLanguage();
        verify(localeSettings, times(3)).getLocale();
        verify(ioService,times(2)).outputText(msg);
        verify(ioService).outputText(outputError);
        verify(localeSettings).changeToRuLocale();
    }


}