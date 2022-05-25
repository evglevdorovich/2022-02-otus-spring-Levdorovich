package ru.otus.library.configuration.router;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Hooks;
import ru.otus.library.domain.Genre;
import ru.otus.library.handler.GenreHandler;
import ru.otus.library.repository.GenreRepository;

import javax.annotation.PostConstruct;
import java.util.List;

import static org.mockito.Mockito.when;

@SpringBootTest(classes = {GenreRouter.class, ObjectMapper.class, GenreHandler.class})
@DisplayName("Genre Router should")
class GenreRouterTest {
    @Autowired
    private RouterFunction<ServerResponse> genresRoute;
    @MockBean
    private GenreRepository genreRepository;
    @Autowired
    private ObjectMapper objectMapper;
    private WebTestClient client;

    @PostConstruct
    private void init() {
        client = WebTestClient.bindToRouterFunction(genresRoute).build();
    }

    @Test
    @DisplayName("Should return correct genre's response")
    void shouldReturnCorrectGenresResponse() throws JsonProcessingException {
        val uriPattern = "/api/genres";
        val genres = List.of(new Genre("1", "genreName"));
        val genresFlux = Flux.just(genres.get(0));
        val jsonGenre = objectMapper.writeValueAsString(genres);

        when(genreRepository.findAll()).thenReturn(genresFlux);

        client.get()
                .uri(uriPattern)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody().json(jsonGenre);
    }

}
