package org.peachSpring.app.services;

import org.peachSpring.app.exceptions.BookNotFoundException;
import org.peachSpring.app.exceptions.CannotDeleteBookException;
import org.peachSpring.app.models.Book;
import org.peachSpring.app.repositories.BooksRepository;
import org.peachSpring.app.util.advanced_search.SearcherChain;
import org.peachSpring.app.util.advanced_search.BookFilterSearcherChain;
import org.peachSpring.app.util.search_config.BookSearchConfig;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BookService {
    private final BooksRepository booksRepository;
    private static int lastPageIndex = -1;

    public BookService(BooksRepository booksRepository) {
        this.booksRepository = booksRepository;
    }

    public List<Book> getAllBooks(){
        return booksRepository.findAll();
    }
    public Book findOne(long id) throws BookNotFoundException {
        Optional<Book> curBook = booksRepository.findById(id);
        if (curBook.isPresent()){
            return curBook.get();
        } else {
            throw new BookNotFoundException(String.format("Book with id %d isn`t found", id));
        }
    }
    @Transactional
    public void save(Book book){
        booksRepository.save(book);
    }
    @Transactional
    public void update(Book book, long id){
        book.setId(id);
        booksRepository.save(book);
    }
    @Transactional
    public void delete(Book book) throws CannotDeleteBookException {
        Book curBook = booksRepository.getOne(book.getId());
        if (!curBook.isIstaken()) {
            booksRepository.delete(curBook);
        } else {
            throw new CannotDeleteBookException(
                    String.format(
                    "Book with id %d cannot be deleted `cause one has an owner", curBook.getId())
            );
        }
    }
    public List<Book> getBooks(BookSearchConfig searchConfig){
        List<Book> list = booksRepository.findAll(PageRequest.of(
                searchConfig.getNumberOfPage(),
                searchConfig.getItemsPerPage())).getContent();
        return new BookFilterSearcherChain(searchConfig).searcherManager(list);
    }

    public int getLastPageIndex() {
        return lastPageIndex;
    }
}
