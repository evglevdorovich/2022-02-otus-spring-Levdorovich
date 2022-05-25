package ru.otus.library.configuration.router;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import ru.otus.library.handler.CommentHandler;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

@Configuration
public class CommentRouter {
    @Bean
    public RouterFunction<ServerResponse> commentsRoute(CommentHandler handler) {
        return RouterFunctions.route(
                GET("/api/comments/{id}")
                        .and(accept(APPLICATION_JSON)), handler::getByBookId);
    }

}
