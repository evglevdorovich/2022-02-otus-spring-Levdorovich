package ru.otus.library.handler;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import ru.otus.library.dto.CommentDto;
import ru.otus.library.repository.CommentRepository;

import javax.annotation.Nonnull;
import java.util.stream.Collectors;

import static org.springframework.web.reactive.function.BodyInserters.fromValue;

@Component
@RequiredArgsConstructor
public class CommentHandler {
    private final CommentRepository repository;
    private final ModelMapper modelMapper;

    @Nonnull
    public Mono<ServerResponse> getByBookId(ServerRequest serverRequest) {
        var id = serverRequest.pathVariable("id");
        var commentsFlux = repository.findByBookId(id);
        return commentsFlux
                .collectList()
                .flatMap(comments -> {
                    var commentsList = comments.stream()
                            .map(comment -> modelMapper.map(comment, CommentDto.class))
                            .collect(Collectors.toList());
                    return ServerResponse.ok()
                            .contentType(MediaType.APPLICATION_JSON)
                            .body(fromValue(commentsList));
                })
                .switchIfEmpty(ServerResponse.notFound().build());
    }
}
