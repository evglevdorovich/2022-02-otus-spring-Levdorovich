package ru.otus.library.rest;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.library.dto.CommentDto;
import ru.otus.library.services.CommentService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;
    private final ModelMapper modelMapper;

    @GetMapping("/api/comments/{bookId}")
    public List<CommentDto> comments(@PathVariable(name = "bookId") long bookId) {
        return commentService.getByBookId(bookId).stream()
                .map(c -> modelMapper.map(c, CommentDto.class))
                .collect(Collectors.toList());
    }
}
