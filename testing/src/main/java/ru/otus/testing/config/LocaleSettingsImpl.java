package ru.otus.testing.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

@Component
@Getter
@Setter
@ConfigurationProperties(prefix = "application.locale")
public class LocaleSettingsImpl implements LocaleSettings {
    private Locale locale;
    private Map<String, String> languageToLocaleTag;

    public LocaleSettingsImpl(@Value("${application.locale.defaultTagLocale}") String defaultTagLocale) {
        locale = Locale.forLanguageTag(defaultTagLocale);
    }

    @Override
    public Map<Integer, String> getOrderToLanguage() {
        Map<Integer, String> orderToLanguage = new TreeMap<>();
        int initIndex = 1;
        for (String language : languageToLocaleTag.keySet()) {
            orderToLanguage.put(initIndex++, language);
        }
        return orderToLanguage;
    }

}
