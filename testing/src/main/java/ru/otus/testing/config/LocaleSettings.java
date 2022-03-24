package ru.otus.testing.config;

import java.util.Locale;
import java.util.Map;

public interface LocaleSettings {
    Locale getLocale();

    void setLocale(Locale locale);

    Map<String,String> getLanguageToLocaleTag();

    Map<Integer,String> getOrderToLanguage();
}
