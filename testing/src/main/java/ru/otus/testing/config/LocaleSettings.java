package ru.otus.testing.config;

import java.util.Locale;

public interface LocaleSettings {
    Locale getLocale();

    void changeToRootLocale();

    void changeToRuLocale();
}
