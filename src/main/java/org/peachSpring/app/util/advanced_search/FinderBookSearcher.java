package org.peachSpring.app.util.advanced_search;

import org.peachSpring.app.models.Book;
import org.peachSpring.app.util.search_config.BookSearchConfig;

import java.util.List;
import java.util.stream.Collectors;

public class FinderBookSearcher extends BookSearcher{
    public FinderBookSearcher(BookSearchConfig bookSearchConfig) {
        super(bookSearchConfig);
    }

    @Override
    protected List<Book> getBooks(List<Book> list) {
        if (bookSearchConfig.getStringToFind()!=null){
            return list.stream().filter(o1->o1.getName().contains(bookSearchConfig.getStringToFind())).collect(Collectors.toList());
        } else {
            return list;
        }
    }

    @Override
    public List<Book>  bookSearcherManager(List<Book> list) {
        List<Book> curArray = getBooks(list);
        return curArray;

    }
}
