package ru.otus.library.configuration.router;

import com.google.gson.Gson;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.library.domain.Author;
import ru.otus.library.domain.Book;
import ru.otus.library.domain.Genre;
import ru.otus.library.dto.BookForUpdateDto;
import ru.otus.library.handler.BookHandler;
import ru.otus.library.repository.AuthorRepository;
import ru.otus.library.repository.BookRepository;
import ru.otus.library.repository.GenreRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@WebFluxTest({BookRouter.class, BookHandler.class})
@DisplayName("Book Router should")
class BookRouterTest {
    @Autowired
    private RouterFunction<ServerResponse> booksRoute;
    @MockBean
    private BookRepository bookRepository;
    @MockBean
    private AuthorRepository authorRepository;
    @MockBean
    private GenreRepository genreRepository;
    @Autowired
    private Gson gson;
    @Captor
    private ArgumentCaptor<Book> bookCaptor;

    private WebTestClient client;

    @BeforeEach
    private void init() {
        client = WebTestClient.bindToRouterFunction(booksRoute).build();
    }

    @Test
    @DisplayName("should return correct getBooks response")
    void getBooksShouldReturnCorrectBooksResponse() {
        val uriPattern = "/api/books";
        val genre = new Genre("genreName");
        val author = new Author("authorName");
        val books = List.of(new Book("1", "name", author, genre));
        val bookFlux = Flux.just(books.get(0));
        val jsonBooks = gson.toJson(books);

        when(bookRepository.findAll()).thenReturn(bookFlux);

        client.get()
                .uri(uriPattern)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody().json(jsonBooks);
    }

    @Test
    @DisplayName("should return correct getBook response")
    void getBookShouldReturnCorrectBooksResponse() {
        val bookId = "bookId";
        val uriPattern = "/api/books/" + bookId;
        val genre = new Genre("genreName");
        val author = new Author("authorName");
        val book = new Book(bookId, "name", author, genre);
        val bookMono = Mono.just(book);
        val jsonBook = gson.toJson(book);

        when(bookRepository.findById(bookId)).thenReturn(bookMono);

        client.get()
                .uri(uriPattern)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody().json(jsonBook);
    }

    @Test
    @DisplayName("should return correct delete response")
    void deleteBookShouldReturnCorrectBooksResponse() {
        val bookId = "bookId";
        val uriPattern = "/api/books/" + bookId;
        when(bookRepository.deleteById(bookId)).thenReturn(Mono.empty());

        client.delete()
                .uri(uriPattern)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @DisplayName("should return correct postBook response")
    void postBookShouldReturnCorrectBooksResponse() {
        val bookId = "bookId";
        val uriPattern = "/api/books/";
        val bookName = "bookName";
        val genre = new Genre("genreName");
        val author = new Author("authorName");
        val book = new Book(bookId, bookName, author, genre);
        val bookWithNullId = new Book(bookName, author, genre);
        val bookDto = new BookForUpdateDto();

        bookDto.setName(bookName);
        bookDto.setAuthorId(author.getId());
        bookDto.setGenreId(genre.getId());

        val jsonBook = gson.toJson(book);

        when(genreRepository.findById(bookDto.getGenreId())).thenReturn(Mono.just(genre));
        when(authorRepository.findById(bookDto.getGenreId())).thenReturn(Mono.just(author));
        when(bookRepository.save(bookCaptor.capture())).thenReturn(Mono.just(book));

        client.post()
                .uri(uriPattern)
                .body(BodyInserters.fromValue(book))
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isCreated()
                .expectBody().json(jsonBook);
        assertThat(bookCaptor.getValue()).usingRecursiveComparison().isEqualTo(bookWithNullId);
    }

    @Test
    @DisplayName("Should return correct putBook response")
    void putBookShouldReturnCorrectBooksResponse() {
        val bookId = "bookId";
        val uriPattern = "/api/books/" + bookId;
        val bookName = "bookName";
        val genre = new Genre("genreName");
        val author = new Author("authorName");
        val book = new Book(bookId, bookName, author, genre);
        val bookDto = new BookForUpdateDto();

        bookDto.setName(bookName);
        bookDto.setAuthorId(author.getId());
        bookDto.setGenreId(genre.getId());

        when(genreRepository.findById(bookDto.getGenreId())).thenReturn(Mono.just(genre));
        when(authorRepository.findById(bookDto.getGenreId())).thenReturn(Mono.just(author));
        when(bookRepository.update(bookCaptor.capture())).thenReturn(Mono.just(book));

        client.put()
                .uri(uriPattern)
                .body(BodyInserters.fromValue(book))
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk();
        assertThat(bookCaptor.getValue()).usingRecursiveComparison().isEqualTo(book);
    }

    @Test
    @DisplayName("with unExisted book should return not found")
    void getUnExistedBookByIdShouldReturnNotFound() {
        val unExistedId = "bookId";
        val uriPattern = "/api/book/" + unExistedId;

        when(bookRepository.findById(unExistedId)).thenReturn(Mono.empty());
        client.get()
                .uri(uriPattern)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    @DisplayName("unExisted book for put should return not found")
    void putUnExistedBookShouldReturnNotFound() {
        val bookId = "bookId";
        val uriPattern = "/api/books/" + bookId;
        val bookName = "bookName";
        val genre = new Genre("genreName");
        val author = new Author("authorName");
        val book = new Book(bookId, bookName, author, genre);
        val bookDto = new BookForUpdateDto();

        bookDto.setName(bookName);
        bookDto.setAuthorId(author.getId());
        bookDto.setGenreId(genre.getId());

        when(genreRepository.findById(bookDto.getGenreId())).thenReturn(Mono.just(genre));
        when(authorRepository.findById(bookDto.getGenreId())).thenReturn(Mono.just(author));
        when(bookRepository.update(bookCaptor.capture())).thenReturn(Mono.empty());

        client.put()
                .uri(uriPattern)
                .body(BodyInserters.fromValue(book))
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isNotFound();

        assertThat(bookCaptor.getValue()).usingRecursiveComparison().isEqualTo(book);
    }

}