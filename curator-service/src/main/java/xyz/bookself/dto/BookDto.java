package xyz.bookself.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BookDto {
    private String id;
    private String title;
    private Set<AuthorDto> authors;
    private Set<String> genres;
    private String blurb;
    private int pages;
    private String thumbnail;
    private String published;
}
