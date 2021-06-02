package xyz.bookself.books.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.bookself.books.domain.Popularity;

public interface PopularityRepository extends JpaRepository<Popularity, Integer> {
}
