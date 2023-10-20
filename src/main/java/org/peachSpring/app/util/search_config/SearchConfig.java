package org.peachSpring.app.util.search_config;

public abstract class SearchConfig {
    protected int itemsPerPage;
    protected int numberOfPage;
    protected String stringToFind;

    public int getItemsPerPage() {
        return itemsPerPage;
    }

    public void setItemsPerPage(int itemsPerPage) {
        this.itemsPerPage = itemsPerPage;
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
}
