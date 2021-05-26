package xyz.bookself.books.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "book_ratings")
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(RatingDTOViews.RatingDTOWithIdView.class)
    private Integer id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    /**
     * Not using ManyToOne here to prevent a circular dependency with the user-service module
     */
    @Column(name = "user_id", nullable = false)
    private Integer userId;

    @Column(nullable = false)
    @JsonView(RatingDTOViews.RatingDTOWithIdView.class)
    private Integer rating;

    @Column
    @JsonView(RatingDTOViews.RatingDTOWithIdView.class)
    private String comment;

    @Column(name = "created_time")
    private LocalDateTime createdTime;

    /**
     * No arg constructor used by JPA
     */
    public Rating() { }

    public Rating(Book book, Integer userId, Integer rating, String comment) {
        this.book = book;
        this.userId = userId;
        this.rating = rating;
        this.comment = comment;
    }
}
