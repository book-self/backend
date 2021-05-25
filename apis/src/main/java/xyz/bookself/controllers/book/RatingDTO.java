package xyz.bookself.controllers.book;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import xyz.bookself.books.domain.Rating;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.time.LocalDateTime;

@Getter
public class RatingDTO {

    @Min(0) @Max(5)
    @JsonProperty("rating")
    private final int rating;
    @JsonProperty("comment")
    private final String comment;
    @JsonProperty("createdTime")
    private final LocalDateTime createdTime;

    @JsonCreator
    public RatingDTO(@JsonProperty("rating") int rating,
                     @JsonProperty("comment") String comment,
                     @JsonProperty("createdTime") LocalDateTime createdTime) {
        this.rating = rating;
        this.comment = comment;
        this.createdTime = createdTime;
    }

    public RatingDTO(Rating rating) {
        this.rating = rating.getRating();
        this.comment = rating.getComment();
        this.createdTime = rating.getCreatedTime();
    }

}
