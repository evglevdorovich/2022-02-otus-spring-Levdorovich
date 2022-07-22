package ru.otus.library_rest.actuators;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;
import ru.otus.library_rest.repository.BookRepository;

@Component
@RequiredArgsConstructor
public class BooksHealthIndicator implements HealthIndicator {

    private final BookRepository bookRepository;

    @Override
    public Health health() {
        var booksCount = bookRepository.count();
        if (booksCount == 0) {
            return Health.down()
                    .withDetail("books health indicator", "No one books in the library")
                    .build();
        } else {
            return Health.up().withDetail("books health indicator", "There are books in the library").build();
        }
    }
}
