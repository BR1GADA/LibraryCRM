package org.peachSpring.app.util.search_config;

import org.peachSpring.app.util.search_config.constants.Filter;

public abstract class SearchConfig {
    protected static final int itemsPerPage = 6;
    protected int numberOfPage;
    protected String stringToFind;

    protected Filter filter;

    public int getItemsPerPage() {
        return itemsPerPage;
    }



    public int getNumberOfPage() {
        return numberOfPage;
    }

    public void setNumberOfPage(int numberOfPage) {
        this.numberOfPage = numberOfPage;
    }

    public String getStringToFind() {
        return stringToFind;
    }

    public void setStringToFind(String stringToFind) {
        this.stringToFind = stringToFind;
    }

    public Filter getFilter() {
        return filter;
    }

    public void setFilter(Filter filter) {
        this.filter = filter;
    }
}
