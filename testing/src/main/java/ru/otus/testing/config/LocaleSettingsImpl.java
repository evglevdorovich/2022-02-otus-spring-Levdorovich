package ru.otus.testing.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.*;

@Setter
@Component
@ConfigurationProperties(prefix = "application.locale")
public class LocaleSettingsImpl implements LocaleSettings {
    @Getter
    private Locale locale;
    private Map<String, String> languageToLocaleTag;

    public LocaleSettingsImpl(@Value("${application.locale.defaultTagLocale}") String defaultTagLocale) {
        locale = Locale.forLanguageTag(defaultTagLocale);
    }

    @Override
    public List<String> getAvailableLanguages() {
        return new ArrayList<>(languageToLocaleTag.keySet());
    }

    @Override
    public String getLocaleTagByLanguage(String language) {
        return languageToLocaleTag.get(language);
    }
}
