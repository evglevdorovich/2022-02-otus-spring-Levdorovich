package ru.otus.testing.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class LocaleSettingsImpl implements LocaleSettings {
    @Getter
    private Locale locale;
    private final String forRuTag;

    public LocaleSettingsImpl(@Value("${application.locales.ruLangTag}") String ruLangTag,
                              @Value("${application.locales.defaultLocale}") String defaultLocale) {
        this.forRuTag = ruLangTag;
        locale = Locale.forLanguageTag(defaultLocale);
    }

    @Override
    public void changeToRootLocale() {
        locale = Locale.ROOT;
    }

    @Override
    public void changeToRuLocale() {
        locale = Locale.forLanguageTag(forRuTag);
    }
}
