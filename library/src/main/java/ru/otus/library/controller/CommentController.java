package ru.otus.library.controller;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.otus.library.dto.CommentDto;
import ru.otus.library.services.CommentService;

import java.util.stream.Collectors;

@RequiredArgsConstructor
@Controller
public class CommentController {

    private final CommentService commentService;
    private final ModelMapper modelMapper;

    @GetMapping("/comments/{bookId}")
    public String comments(@PathVariable(name = "bookId") long bookId, Model model) {
        var comments = commentService.getByBookId(bookId).stream()
                .map(c -> modelMapper.map(c, CommentDto.class))
                .collect(Collectors.toList());

        model.addAttribute("comments", comments);
        return "comments";
    }
}
