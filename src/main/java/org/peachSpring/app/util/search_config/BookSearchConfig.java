package org.peachSpring.app.util.search_config;

import org.peachSpring.app.util.search_config.constants.BookFilter;

public class BookSearchConfig {
    private BookFilter filter;
    private int booksPerPage;
    private int numberOfPage;
    private String stringToFind;

    public BookFilter getFilter() {
        return filter;
    }

    public void setFilter(BookFilter filter) {
        this.filter = filter;
    }

    public int getBooksPerPage() {
        return booksPerPage;
    }

    public void setBooksPerPage(int booksPerPage) {
        this.booksPerPage = booksPerPage;
    }

    public int getNumberOfPage() {
        return numberOfPage;
    }

    public void setNumberOfPage(int numberOfPage) {
        this.numberOfPage = numberOfPage;
    }

    public BookSearchConfig() {
    }

    public String getStringToFind() {
        return stringToFind;
    }

    public void setStringToFind(String stringToFind) {
        this.stringToFind = stringToFind;
    }
}
