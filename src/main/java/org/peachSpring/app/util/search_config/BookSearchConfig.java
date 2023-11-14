package org.peachSpring.app.util.search_config;

import org.peachSpring.app.models.Genre;
import org.peachSpring.app.util.search_config.constants.BookFilter;

public class BookSearchConfig extends SearchConfig {
    private BookFilter filter;

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    private String genre;

    public BookFilter getFilter() {
        return filter;
    }

    public void setFilter(BookFilter filter) {
        this.filter = filter;
    }

    public BookSearchConfig() {
    }


}
