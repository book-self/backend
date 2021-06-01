package xyz.bookself.controllers.recommendationEngine;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import xyz.bookself.books.domain.AverageRating;
import xyz.bookself.books.domain.Book;
import xyz.bookself.controllers.book.AuthorDTO;


import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Collections.emptySet;

@Getter
public class BookRecommendationDTO {

    @JsonProperty("id")
    private final String id;
    @JsonProperty("title")
    private final String title;
    @JsonProperty("genres")
    private final Set<String> genres;
    @JsonProperty("authors")
    private final Set<AuthorDTO> authors;
    @JsonProperty("averageRating")
    private final Double averageRating;

    @JsonCreator
    public BookRecommendationDTO(@JsonProperty("id") String id,
                                 @JsonProperty("title") String title,
                                 @JsonProperty("genres") Set<String> genres,
                                 @JsonProperty("authors") Set<AuthorDTO> authors,
                                 @JsonProperty("averageRating") Double averageRating) {
        this.id = id;
        this.title = title;
        this.genres = genres;
        this.authors = authors;
        this.averageRating = averageRating;}

    public BookRecommendationDTO(Book book) {
        this.id = book.getId();
        this.title = book.getTitle();
        this.genres = book.getGenres() == null ? emptySet() : book.getGenres();
        this.authors =  book.getAuthors() == null ? emptySet() : book.getAuthors().stream().map(AuthorDTO::new).collect(Collectors.toSet());
        this.averageRating = Optional.of(book).map(Book::getAverageRating).map(AverageRating::getAverageRating).orElse(null); // Mapping so we can flatten, still a null if not found
    }
}



