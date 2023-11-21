package org.peachSpring.app.services;

import org.peachSpring.app.models.Book;
import org.peachSpring.app.models.Book_User;
import org.peachSpring.app.models.User;
import org.peachSpring.app.repositories.BooksRepository;
import org.peachSpring.app.repositories.BooksUsersRepository;
import org.peachSpring.app.repositories.UsersRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@Transactional(readOnly = true)
public class BooksUsersService {
    private final BooksUsersRepository booksUsersRepository;
    private final UsersRepository usersRepository;
    private final BooksRepository booksRepository;

    public BooksUsersService(BooksUsersRepository booksUsersRepository, UsersRepository usersRepository, BooksRepository booksRepository) {
        this.booksUsersRepository = booksUsersRepository;
        this.usersRepository = usersRepository;
        this.booksRepository = booksRepository;
    }

    public Book_User findFirstByBookIdOrderByTimeDesc(long bookId) {
        return booksUsersRepository.findFirstByBookIdOrderByTimeDesc(bookId);
    }
    public Book_User findFirstByUserIdOrderByTimeDesc(long userId){
        return booksUsersRepository.findFirstByUserIdOrderByTimeDesc(userId);
    }

    @Transactional
    public void appointBook(User user, long bookId) {
        Date curDate = new Date();
        booksUsersRepository.save(new Book_User(user.getId(), bookId, curDate));
        User curUser = usersRepository.getOne(user.getId());
        Book curBook = booksRepository.getOne(bookId);
        curUser.setHasPass(true);
        curBook.setApproved(true);
    }

    @Transactional
    public void releaseBook(long id) {
        Book curBook = booksRepository.getOne(id);
        Book_User currentRelation = findFirstByBookIdOrderByTimeDesc(id);
        User owner = usersRepository.getOne(currentRelation.getUserId());
        owner.setHasPass(false);
        curBook.setApproved(false);
    }


}
