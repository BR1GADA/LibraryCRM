package org.peachSpring.app.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.peachSpring.app.exceptions.BookNotFoundException;
import org.peachSpring.app.exceptions.CannotDeleteBookException;
import org.peachSpring.app.models.Book;
import org.peachSpring.app.models.User;
import org.peachSpring.app.security.UsersDetails;
import org.peachSpring.app.services.BookService;
import org.peachSpring.app.services.BooksUsersService;
import org.peachSpring.app.services.GenresService;
import org.peachSpring.app.services.UserService;

import org.peachSpring.app.util.search_config.BookSearchConfig;
import org.peachSpring.app.util.validators.BookValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;



@Controller
@RequestMapping("/books")
public class BooksController {

    private final UserService userService;
    private final BookService bookService;
    private final BooksUsersService booksUsersService;


    private final BookValidator bookValidator;
    private final GenresService genresService;

    @Autowired
    public BooksController(UserService userService1, BookService bookService1, BooksUsersService booksUsersService, BookValidator bookValidator, GenresService genresService) {
        this.userService = userService1;
        this.bookService = bookService1;
        this.booksUsersService = booksUsersService;
        this.bookValidator = bookValidator;

        this.genresService = genresService;
    }

    @GetMapping()
    public String booksIndex(HttpServletRequest httpServletRequest,
                             Model model,
                             @ModelAttribute("searchConfig") BookSearchConfig searchConfig){

        int numberOfPage = 0;
        try {
            numberOfPage = Integer.parseInt(httpServletRequest.getParameter("page"));
            if (numberOfPage<0){
                numberOfPage = 0;
            }
        } catch (NumberFormatException ignore) {}
        searchConfig.setNumberOfPage(numberOfPage);
        User curUser = UserService.getCurrentUsersPrinciples();
        model.addAttribute("genre", searchConfig.getGenre());
        model.addAttribute("usersName", curUser.getName());
        model.addAttribute("allBooks", bookService.getBooks(searchConfig));
        model.addAttribute("numberOfPage", numberOfPage);
        model.addAttribute("stringToFind", httpServletRequest.getParameter("stringToFind"));
        model.addAttribute("genres", genresService.findAll());
        model.addAttribute("usersRole", curUser.getRole());
        return "books/index";
    }


    @GetMapping("/new")
    public String requestToAddNewBook(Model model,
                                      @ModelAttribute("bookToAdd") Book book){
        model.addAttribute("genres", genresService.findAll());
        return "books/new";
    }

    @PostMapping()
    public String addNewBook(Model model,
                             @ModelAttribute("bookToAdd") @Valid Book book,
                             BindingResult bindingResult){
        System.out.println(book.getYear());
        bookValidator.validate(book,bindingResult);
        if (bindingResult.hasErrors()){
            model.addAttribute("genres", genresService.findAll());
            return "books/new";
        }
        bookService.save(book);
        return "redirect:/books";
    }
    @GetMapping("/{id}")
    public String getOneBook(@PathVariable("id") long id, Model model){
        Book curBook = null;
        try {
            curBook = bookService.findOne(id);
        } catch (BookNotFoundException e) {
            e.printStackTrace();
            return "errors/bookNotFound";
        }
        /*Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UsersDetails usersDetails = (UsersDetails) authentication.getPrincipal();*/
        User curUser = UserService.getCurrentUsersPrinciples();
        model.addAttribute("curBook",curBook);
        model.addAttribute("usersName", curUser.getName());
        model.addAttribute("usersRole", curUser.getRole());
        return "books/book";
    }




    @GetMapping("/{id}/edit")
    public String requestToEditBook(@PathVariable("id") long id,
                                    Model model){
        try {
            model.addAttribute("curBook", bookService.findOne(id));
        } catch (BookNotFoundException e) {
            e.printStackTrace();
            return "errors/bookNotFound";
        }
        User curUser = UserService.getCurrentUsersPrinciples();
        model.addAttribute("genres", genresService.findAll());
        model.addAttribute("usersName", curUser.getName());
        return "books/edit";
    }

    @PatchMapping("/{id}")
    public String editingBook(Model model,
                              @ModelAttribute("curBook") @Valid Book book,
                              BindingResult bindingResult,
                              @PathVariable("id") long id){
        bookValidator.validate(book,bindingResult);
        if (bindingResult.hasErrors()){
            User curUser = UserService.getCurrentUsersPrinciples();
            model.addAttribute("genres", genresService.findAll());
            model.addAttribute("usersName", curUser.getName());
            return "books/edit";
        }
        bookService.update(book,id);

        return "redirect:/books/{id}";
    }
    @DeleteMapping("/{id}")
    public String deleteBook(@ModelAttribute("curBook")Book book,
                             @PathVariable("id") long id){


        try {
            bookService.delete(book);
        } catch (CannotDeleteBookException e) {
            e.printStackTrace();
            return "errors/cannotDeleteBook";
        }
        return "redirect:/books";
    }







}
