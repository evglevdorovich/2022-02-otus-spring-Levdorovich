package ru.otus.testing.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageIOServiceImpl implements MessageIOService {
    private final IOService ioService;
    private final MessageService messageService;

    @Override
    public void outputMessageByCode(String code, Object... args) {
        String message = messageService.getMessage(code, args);
        ioService.outputText(message);
    }

    @Override
    public List<Integer> inputCommaSeparatedIntegersWithPromptAndWarning(String promptCode, String warningCode) {
        String prompt = messageService.getMessage(promptCode);
        String warning = messageService.getMessage(warningCode);
        return ioService.inputCommaSeparatedIntegersWithPromptAndWarning(prompt, warning);
    }

    @Override
    public String inputText() {
        return ioService.inputText();
    }

    @Override
    public void outputText(String text) {
        ioService.outputText(text);
    }
}
