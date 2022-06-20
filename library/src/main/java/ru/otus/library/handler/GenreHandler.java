package ru.otus.library.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import ru.otus.library.repository.GenreRepository;

import javax.annotation.Nonnull;

import static org.springframework.web.reactive.function.BodyInserters.fromValue;

@Component
@RequiredArgsConstructor
public class GenreHandler {
    private final GenreRepository repository;

    @Nonnull
    public Mono<ServerResponse> getAll(ServerRequest serverRequest) {
        var genres = repository.findAll();
        return genres
                .collectList()
                .flatMap(genre -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(fromValue(genre)))
                .switchIfEmpty(ServerResponse.notFound().build());
    }
}
