package ru.otus.library.facade;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.library.converter.AuthorViewConverter;
import ru.otus.library.services.AuthorService;

@Service
@RequiredArgsConstructor
public class AuthorManagerServiceImpl implements AuthorManagerService {
    private final AuthorViewConverter authorViewConverter;
    private final AuthorService authorService;

    @Override
    public String getAllView() {
        return authorViewConverter.getViewAuthors(authorService.getAll());
    }
}
