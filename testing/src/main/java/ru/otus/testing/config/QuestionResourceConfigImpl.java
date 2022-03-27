package ru.otus.testing.config;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Locale;
import java.util.Map;

@ConfigurationProperties(prefix = "questions")
@Component
@Setter
@RequiredArgsConstructor
public class QuestionResourceConfigImpl implements QuestionResourceConfig {
    private Map<String, String> localeTagToResourcePath;
    private String rootResourcePath;
    private final LocaleSettings localeSettings;

    @Override
    public String getResourcePath() {
        if (isRootLocale()){
            return rootResourcePath;
        }
        else {
            return localeTagToResourcePath.get(localeSettings.getLocale().getLanguage());
        }
    }

    private boolean isRootLocale() {
        return localeSettings.getLocale().equals(Locale.ROOT);
    }

}
