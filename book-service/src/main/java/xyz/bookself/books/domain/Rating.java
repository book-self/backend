package xyz.bookself.books.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import xyz.bookself.users.domain.User;

import javax.persistence.*;

@Data
@Entity
@Table(name = "book_ratings")
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private Integer rating;

    @Column
    private String comment;

    public Rating() { }

    public Rating(Book book, User user, Integer rating, String comment) {
        this.book = book;
        this.user = user;
        this.rating = rating;
        this.comment = comment;
    }
}
