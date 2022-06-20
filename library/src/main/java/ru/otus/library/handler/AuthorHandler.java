package ru.otus.library.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import ru.otus.library.repository.AuthorRepository;

import javax.annotation.Nonnull;

import static org.springframework.web.reactive.function.BodyInserters.fromValue;

@Component
@RequiredArgsConstructor
public class AuthorHandler {
    private final AuthorRepository repository;

    @Nonnull
    public Mono<ServerResponse> getAll(ServerRequest serverRequest) {
        var authors = repository.findAll();
        return authors
                .collectList()
                .flatMap(author -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(fromValue(author)))
                .switchIfEmpty(ServerResponse.notFound().build());
    }
}
