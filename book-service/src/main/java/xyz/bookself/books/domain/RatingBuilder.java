package xyz.bookself.books.domain;

import xyz.bookself.users.domain.User;

public final class RatingBuilder {
    private Book book;
    private User user;
    private Integer rating;
    private String comment;

    private RatingBuilder() {
    }

    public static RatingBuilder aRating() {
        return new RatingBuilder();
    }

    public RatingBuilder withBook(Book book) {
        this.book = book;
        return this;
    }

    public RatingBuilder withUser(User user) {
        this.user = user;
        return this;
    }

    public RatingBuilder withRating(Integer rating) {
        this.rating = rating;
        return this;
    }

    public RatingBuilder withComment(String comment) {
        this.comment = comment;
        return this;
    }

    public Rating build() {
        return new Rating(book, user, rating, comment);
    }
}
