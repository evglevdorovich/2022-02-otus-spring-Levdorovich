package ru.otus.testing.services;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.otus.testing.config.LocaleSettings;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {
    private final MessageSource messageSource;
    private final LocaleSettings localeSettings;

    @Override
    public String getMessage(String code, Object... args) {
        return messageSource.getMessage(code, args, localeSettings.getLocale());
    }

}
