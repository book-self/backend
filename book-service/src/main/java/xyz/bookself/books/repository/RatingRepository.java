package xyz.bookself.books.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import xyz.bookself.books.domain.Rating;

import java.util.Optional;


public interface RatingRepository extends CrudRepository<Rating, Integer> {
    @Query(nativeQuery = true, value = "SELECT * FROM book_ratings WHERE user_id = ?1 AND book_id = ?2")
    Optional<Rating> findRatingByUserForBook(int userId, String bookId);
}
