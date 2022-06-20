package ru.otus.library.configuration.router;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import ru.otus.library.handler.BookHandler;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;

@Configuration
public class BookRouter {

    @Bean
    public RouterFunction<ServerResponse> booksRoute(BookHandler handler) {
        return RouterFunctions.route(
                        GET("/api/books/{id}")
                                .and(accept(APPLICATION_JSON)), handler::getBookById)
                .andRoute(GET("/api/books")
                        .and(accept(APPLICATION_JSON)), handler::getAll)
                .andRoute(DELETE("/api/books/{id}")
                        .and(accept(APPLICATION_JSON)), handler::deleteById)
                .andRoute(POST("/api/books")
                        .and(accept(APPLICATION_JSON)), handler::createBook)
                .andRoute(PUT("/api/books/{id}")
                        .and(accept(APPLICATION_JSON)), handler::updateBook);
    }
}
