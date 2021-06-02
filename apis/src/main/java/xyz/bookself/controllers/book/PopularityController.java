package xyz.bookself.controllers.book;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import xyz.bookself.books.repository.GenrePopularityRepository;
import xyz.bookself.books.repository.PopularityRepository;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping(PopularityController.POPULAR_ENDPOINT)
@Slf4j
public class PopularityController {
    public static final String POPULAR_ENDPOINT = "/v1/books/populars";

    private final PopularityRepository popularityRepository;
    private final GenrePopularityRepository genrePopularityRepository;

    @Autowired
    public PopularityController(PopularityRepository popularityRepository,
                                GenrePopularityRepository genrePopularityRepository) {
        this.popularityRepository = popularityRepository;
        this.genrePopularityRepository = genrePopularityRepository;
    }

    @GetMapping("")
    public ResponseEntity<Collection<PopularityDTO>> getPopularBooks(@RequestParam Optional<String> genre) {
        final String byGenre = genre.orElse("");
        final int limit = 10;
        return "".equals(byGenre)
                ? new ResponseEntity<>(popularityRepository.findAll().stream().map(PopularityDTO::new).collect(Collectors.toList()), HttpStatus.OK)
                : new ResponseEntity<>(genrePopularityRepository.getPopularBooksByGenre(byGenre, limit).stream().map(PopularityDTO::new).collect(Collectors.toList()), HttpStatus.OK);
    }
}
