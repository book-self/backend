package xyz.bookself.controllers.book;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.bookself.books.repository.PopularityRepository;

import java.util.Collection;
import java.util.stream.Collectors;

@RestController
@RequestMapping(PopularityController.POPULAR_ENDPOINT)
@Slf4j
public class PopularityController {
    public static final String POPULAR_ENDPOINT = "/v1/books/populars";

    private final PopularityRepository popularityRepository;

    @Autowired
    public PopularityController(PopularityRepository repository) {
        this.popularityRepository = repository;
    }

    @GetMapping("")
    public ResponseEntity<Collection<PopularityDTO>> ratings() {
        return new ResponseEntity<>(
                popularityRepository.findAll().stream().map(PopularityDTO::new).collect(Collectors.toList()),
                HttpStatus.OK
        );
    }
}
