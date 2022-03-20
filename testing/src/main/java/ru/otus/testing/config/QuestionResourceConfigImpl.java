package ru.otus.testing.config;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import ru.otus.testing.config.enums.Language;
import ru.otus.testing.exceptions.NoSuchLocaleException;

import java.util.Arrays;
import java.util.Map;

@ConfigurationProperties(prefix = "questions")
@Component
@Setter
@RequiredArgsConstructor
public class QuestionResourceConfigImpl implements QuestionResourceConfig {
    private Map<Language, String> resourcePath;
    private final LocaleSettings localeSettings;

    @Override
    public String getResourcePathForCurrentLocale() {
        String locale = localeSettings.getLocale().toString();
        Language language = Arrays.stream(Language.values())
                .filter(l -> l.getLocalePath().equals(locale))
                .findAny()
                .orElseThrow(() -> new NoSuchLocaleException(locale));
        return resourcePath.get(language);
    }
}
