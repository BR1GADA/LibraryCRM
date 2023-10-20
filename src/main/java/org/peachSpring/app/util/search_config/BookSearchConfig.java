package org.peachSpring.app.util.search_config;

import org.peachSpring.app.util.search_config.constants.BookFilter;

public class BookSearchConfig extends SearchConfig {
    private BookFilter filter;

    public BookFilter getFilter() {
        return filter;
    }

    public void setFilter(BookFilter filter) {
        this.filter = filter;
    }

    public BookSearchConfig() {
    }


}
