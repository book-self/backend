package books.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
@Table(name = "books")
public class Book {

    @Id
    private String id;
    private String title;
    @OneToMany
    private Set<Author> authors;
    @ElementCollection
    private Set<String> genres;
    private String blurb;
    private int pages;
    private String thumbnail;
    private String published;
}
