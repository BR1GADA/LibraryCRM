package org.peachSpring.app.util.advanced_search;

import org.peachSpring.app.models.Book;
import org.peachSpring.app.util.search_config.BookSearchConfig;
import org.peachSpring.app.util.search_config.constants.BookFilter;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class BookFilterSearcherChain extends SearcherChain<Book> {
    public BookFilterSearcherChain(BookSearchConfig bookSearchConfig) {
        super(bookSearchConfig);
        this.setNextSearcherChain(new BookFinderSearcherChain(bookSearchConfig));
    }

    @Override
    protected List<Book> getItems(List<Book> list) {

        if (searchConfig.getFilter() == null) {
            return list;
        }
        switch ((BookFilter) searchConfig.getFilter()) {
            case YEAR:
                return list.stream().sorted(Comparator.comparingInt(Book::getYear)).collect(Collectors.toList());
            default:
                return list;
        }
    }



}
