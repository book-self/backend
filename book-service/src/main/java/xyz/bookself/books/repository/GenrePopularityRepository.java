package xyz.bookself.books.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import xyz.bookself.books.domain.GenrePopularity;

import java.util.Collection;

public interface GenrePopularityRepository extends JpaRepository<GenrePopularity, Integer> {
    @Query(nativeQuery = true, value = "SELECT * FROM (SELECT * FROM popular_books_by_genre WHERE genre = ?1 ORDER BY ranked_time DESC) as latest_ranks ORDER BY rank LIMIT ?2")
    Collection<GenrePopularity> getPopularBooksByGenre(String genre, int limit);
}
