package ru.otus.library.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class SecurityController {

    @GetMapping("/error")
    public String errorPage() {
        return "error";
    }


    @GetMapping("/403")
    public String accessDeniedPage() {
        return "/403";
    }
}
