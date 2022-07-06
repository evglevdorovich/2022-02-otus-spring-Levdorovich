package ru.otus.library.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.library.domain.Author;
import ru.otus.library.domain.Genre;
import ru.otus.library.services.AuthorService;
import ru.otus.library.services.GenreService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthorController.class)
@DisplayName("Author controller should")
class AuthorControllerTest {

    @Autowired
    private MockMvc mvc;
    @MockBean
    private AuthorService authorService;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("return expected authors")
    void shouldReturnExpectedAuthors() throws Exception {
        var author = new Author(1, "authorName");
        var authors = List.of(author);
        given(authorService.getAll()).willReturn(authors);

        var expectedJson = objectMapper.writeValueAsString(authors);

        mvc.perform(get("/api/authors"))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));
    }

}