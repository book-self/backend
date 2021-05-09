package xyz.bookself.controllers.book;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/v1/genres")
public class GenreController {

    @GetMapping("/all")
    public Collection<String> getAllGenres() {
        return null;
    }
}
