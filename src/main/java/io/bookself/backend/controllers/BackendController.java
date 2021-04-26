package io.bookself.backend.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BackendController {

    @GetMapping("/ping")
    public String ping() {
        return "pong";
    }

}
