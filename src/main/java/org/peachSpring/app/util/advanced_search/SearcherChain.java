package org.peachSpring.app.util.advanced_search;

import org.peachSpring.app.models.Book;
import org.peachSpring.app.util.search_config.SearchConfig;

import java.util.List;

public abstract class SearcherChain<T> {
    protected SearchConfig searchConfig;
    protected SearcherChain<T> nextSearcherChain;

    public SearcherChain(SearchConfig searchConfig) {
        this.searchConfig = searchConfig;
    }

    public void setNextSearcherChain(SearcherChain<T> searcherChain) {
        this.nextSearcherChain = searcherChain;
    }
    protected abstract List<T> getItems(List<T> list);
    public List<T> searcherManager(List<T> list){
        List<T> curArray = getItems(list);
        if (this.nextSearcherChain!=null){
            return this.nextSearcherChain.searcherManager(curArray);
        } else {
            return curArray;
        }

    };
}
