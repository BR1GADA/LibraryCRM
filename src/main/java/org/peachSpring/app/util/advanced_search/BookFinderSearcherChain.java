package org.peachSpring.app.util.advanced_search;

import org.peachSpring.app.models.Book;
import org.peachSpring.app.util.search_config.BookSearchConfig;

import java.util.List;
import java.util.stream.Collectors;

public class BookFinderSearcherChain extends SearcherChain<Book> {
    public BookFinderSearcherChain(BookSearchConfig bookSearchConfig) {
        super(bookSearchConfig);
    }

    @Override
    protected List<Book> getItems(List<Book> list) {
        if (searchConfig.getStringToFind()!=null){
            return list.stream().filter(o1->o1.getName().
                    toLowerCase().
                    contains(searchConfig.getStringToFind().toLowerCase())).
                    collect(Collectors.toList());
        } else {
            return list;
        }
    }

}
