package org.peachSpring.app.util.advanced_search;

import org.peachSpring.app.models.Book;
import org.peachSpring.app.util.search_config.BookSearchConfig;
import org.peachSpring.app.util.search_config.SearchConfig;

import java.util.List;
import java.util.stream.Collectors;

public class BookGenreSearcherChain extends SearcherChain<Book>{
    private BookSearchConfig bookSearchConfig;
    public BookGenreSearcherChain(BookSearchConfig searchConfig) {
        super(searchConfig);
        this.bookSearchConfig = searchConfig;
    }

    @Override
    protected List<Book> getItems(List<Book> list) {
        if (bookSearchConfig.getGenre()!=null && !bookSearchConfig.getGenre().equals("")){
            return list.stream().filter(book -> book.getGenre().getGenreName().equals(bookSearchConfig.getGenre())).collect(Collectors.toList());
        } else {
            return list;
        }
    }
}
