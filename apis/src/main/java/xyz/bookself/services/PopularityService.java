package xyz.bookself.services;

import org.springframework.stereotype.Service;
import xyz.bookself.books.domain.Book;
import xyz.bookself.books.domain.Rating;
import xyz.bookself.books.repository.RatingRepository;
import xyz.bookself.config.BookselfApiConfiguration;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toSet;

@Service
public class PopularityService {

    private final RatingRepository ratingRepository;
    private final BookselfApiConfiguration apiConfiguration;

    public PopularityService(RatingRepository ratingRepository, BookselfApiConfiguration configuration) {
        this.ratingRepository = ratingRepository;
        this.apiConfiguration = configuration;
    }

    /**
     * Scheduled Task that persists ranks in a table.
     */
    public void getRankingsByRating() {
        final Map<Book, Set<Rating>> ratingsGroupedByBooks = getRatingsGroupedByBook();
        final Map<Book, Double> averageRatings = new HashMap<>();
        ratingsGroupedByBooks.keySet().forEach(book -> {
            final Set<Rating> ratings = ratingsGroupedByBooks.get(book);
            final Double averageRating = ratings.stream().map(Rating::getRating).reduce(0, Integer::sum) * 1.0 / ratings.size();
            averageRatings.put(book, averageRating);
        });
        averageRatings.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .limit(apiConfiguration.getMaxPopularBooksCount())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue,
                        LinkedHashMap::new
                ))
                .keySet()
                .forEach(withRankCounter((i, book) -> {
                    System.out.printf("%d. %s%n", i, book.getId());
                }));
    }

    private Map<Book, Set<Rating>> getRatingsGroupedByBook() {
        final Collection<Rating> ratings = new HashSet<>();
        ratingRepository.findAll().forEach(ratings::add);
        return ratings.stream().collect(groupingBy(Rating::getBook, toSet()));
    }

    private static <T> Consumer<T> withRankCounter(BiConsumer<Integer, T> consumer) {
        AtomicInteger counter = new AtomicInteger(1);
        return item -> consumer.accept(counter.getAndIncrement(), item);
    }
}

