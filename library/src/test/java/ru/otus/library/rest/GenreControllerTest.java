package ru.otus.library.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.library.domain.Book;
import ru.otus.library.domain.Comment;
import ru.otus.library.domain.Genre;
import ru.otus.library.dto.CommentDto;
import ru.otus.library.services.CommentService;
import ru.otus.library.services.GenreService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(GenreController.class)
@DisplayName("Genre controller should")
class GenreControllerTest {

    @Autowired
    private MockMvc mvc;
    @MockBean
    private GenreService genreService;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("return expected genres")
    void shouldReturnExpectedGenres() throws Exception {
        var genre = new Genre(1, "genreName");
        var genres = List.of(genre);
        given(genreService.getAll()).willReturn(genres);

        var expectedJson = objectMapper.writeValueAsString(genres);

        mvc.perform(get("/api/genres"))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));
    }

}