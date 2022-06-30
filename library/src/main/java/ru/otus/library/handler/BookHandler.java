package ru.otus.library.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import ru.otus.library.domain.Book;
import ru.otus.library.dto.BookForUpdateDto;
import ru.otus.library.repository.AuthorRepository;
import ru.otus.library.repository.BookRepository;
import ru.otus.library.repository.CommentRepository;
import ru.otus.library.repository.GenreRepository;

import javax.annotation.Nonnull;
import java.net.URI;
import java.util.Optional;

import static org.springframework.web.reactive.function.BodyInserters.fromValue;

@Component
@RequiredArgsConstructor
public class BookHandler {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;
    private final CommentRepository commentRepository;

    @Nonnull
    public Mono<ServerResponse> getBookById(ServerRequest serverRequest) {
        var id = serverRequest.pathVariable("id");
        var book = bookRepository.findById(id);
        return book
                .flatMap(b -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(fromValue(b)))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    @Nonnull
    public Mono<ServerResponse> getAll(ServerRequest serverRequest) {
        var books = bookRepository.findAll();
        return books
                .collectList()
                .flatMap(b -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(fromValue(b)));
    }

    @Nonnull
    public Mono<ServerResponse> deleteById(ServerRequest serverRequest) {
        var id = serverRequest.pathVariable("id");
        return Mono.zip(commentRepository.deleteByBookId(id),
                        bookRepository.deleteById(id))
                .flatMap(val -> ServerResponse.ok().build());
    }

    @Nonnull
    public Mono<ServerResponse> createBook(ServerRequest serverRequest) {
        var bookFromRequest = serverRequest.bodyToMono(BookForUpdateDto.class);
        return bookFromRequest
                .flatMap(dtoBook -> {
                    var genreMono = genreRepository.findById(dtoBook.getGenreId());
                    var authorMono = authorRepository.findById(dtoBook.getAuthorId());
                    return genreMono
                            .zipWith(authorMono, (genre, author) -> new Book(dtoBook.getName(), author, genre))
                            .flatMap(bookRepository::save);
                })
                .flatMap(book -> ServerResponse.created(URI.create("/api/books/" + book.getId()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromValue(book)));
    }


    @Nonnull
    public Mono<ServerResponse> updateBook(ServerRequest serverRequest) {
        var bookId = serverRequest.pathVariable("id");
        return serverRequest.bodyToMono(BookForUpdateDto.class)
                .flatMap(dtoBook -> {
                    var genreMono = genreRepository.findById(dtoBook.getGenreId());
                    var authorMono = authorRepository.findById(dtoBook.getAuthorId());
                    return genreMono
                            .zipWith(authorMono, (genre, author) -> new Book(bookId, dtoBook.getName(), author, genre))
                            .flatMap(bookRepository::insert);
                })
                .flatMap(book -> ServerResponse.ok().build())
                .switchIfEmpty(ServerResponse.notFound().build());

    }

}
