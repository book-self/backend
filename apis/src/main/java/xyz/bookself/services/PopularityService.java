package xyz.bookself.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import xyz.bookself.books.domain.Book;
import xyz.bookself.books.domain.Popularity;
import xyz.bookself.books.domain.Rating;
import xyz.bookself.books.repository.PopularityRepository;
import xyz.bookself.books.repository.RatingRepository;
import xyz.bookself.config.BookselfApiConfiguration;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toSet;

@Service
@Slf4j
public class PopularityService {

    private final RatingRepository ratingRepository;
    private final BookselfApiConfiguration apiConfiguration;
    private final PopularityRepository popularityRepository;

    public PopularityService(RatingRepository ratingRepository,
                             BookselfApiConfiguration configuration,
                             PopularityRepository popularityRepository) {
        this.ratingRepository = ratingRepository;
        this.apiConfiguration = configuration;
        this.popularityRepository = popularityRepository;
    }

    @Scheduled(cron = "0 35 * * * *")
    public void getRankingsByRating() {
        log.info("BEGIN SCHEDULED TASK - Computing Popularity");
        final Map<Book, Set<Rating>> ratingsGroupedByBooks = getRatingsGroupedByBook();
        final Map<Book, Double> averageRatings = new HashMap<>();
        ratingsGroupedByBooks.keySet().forEach(book -> {
            final Set<Rating> ratings = ratingsGroupedByBooks.get(book);
            final Double averageRating = (1.0 / ratings.size()) * ratings.stream()
                    .map(Rating::getRating)
                    .reduce(0, Integer::sum);
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
                .forEach(withRankCounter((rank, book) -> {
                    final Popularity popularity = new Popularity();
                    popularity.setBook(book);
                    popularity.setRank(rank);
                    popularity.setRankedTime(LocalDateTime.now(ZoneId.of("UTC")));
                    popularityRepository.save(popularity);
                }));
        log.info("END SCHEDULED TASK - Computing Popularity");
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

