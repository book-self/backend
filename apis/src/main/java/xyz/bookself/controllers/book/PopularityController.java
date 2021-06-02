package xyz.bookself.controllers.book;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.bookself.books.domain.Book;
import xyz.bookself.books.repository.BookRepository;
import xyz.bookself.books.repository.RatingRepository;
import xyz.bookself.services.PopularityService;

import java.util.Map;

@RestController
@RequestMapping(PopularityController.POPULAR_ENDPOINT)
@Slf4j
public class PopularityController {
    public static final String POPULAR_ENDPOINT = "/v1/books/populars";

    private final RatingRepository ratingRepository;
    private final BookRepository bookRepository;
    private final PopularityService popularityService;

    @Autowired
    public PopularityController(RatingRepository ratingRepository,
                                BookRepository bookRepository,
                                PopularityService service) {
        this.ratingRepository = ratingRepository;
        this.bookRepository = bookRepository;
        this.popularityService = service;
    }

    @GetMapping("")
    public Integer ratings() {
        final Map<Book, Double> rankings = popularityService.getRankingsByRating();
        rankings.keySet().stream().limit(20).forEach(r -> log.info(r.getId() + " -> " + rankings.get(r)));
        return rankings.size();
    }
}
