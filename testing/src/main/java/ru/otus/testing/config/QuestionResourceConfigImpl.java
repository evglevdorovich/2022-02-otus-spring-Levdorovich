package ru.otus.testing.config;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Locale;

@ConfigurationProperties(prefix = "questions")
@Component
@Setter
@RequiredArgsConstructor
public class QuestionResourceConfigImpl implements QuestionResourceConfig {
    private String rootResourcePath;
    private final LocaleSettings localeSettings;

    @Override
    public String getResourcePath() {
        if (isCurrentRoot()) {
            return rootResourcePath;
        } else {
            return getResourcePathForCurrentLocale();
        }
    }

    private String getResourcePathForCurrentLocale() {
        int lastPointIndex = rootResourcePath.lastIndexOf('.');
        String resourceName = rootResourcePath.substring(0,lastPointIndex);
        String localName = "_" + localeSettings.getLocale().toLanguageTag();
        String fileFormat = rootResourcePath.substring(lastPointIndex);
        return resourceName + localName + fileFormat;
    }

    private boolean isCurrentRoot() {
        return Locale.ROOT.equals(localeSettings.getLocale());
    }
}
