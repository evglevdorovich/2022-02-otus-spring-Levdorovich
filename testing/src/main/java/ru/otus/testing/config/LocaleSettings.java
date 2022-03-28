package ru.otus.testing.config;

import java.util.List;
import java.util.Locale;

public interface LocaleSettings {
    Locale getLocale();

    void setLocale(Locale locale);

    List<String> getAvailableLanguages();

    String getLocaleTagByLanguage(String language);
}
