package org.peachSpring.app.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.peachSpring.app.exceptions.CannotDeleteUserException;
import org.peachSpring.app.exceptions.UserNotFoundException;
import org.peachSpring.app.models.User;
import org.peachSpring.app.services.BookService;
import org.peachSpring.app.services.BooksUsersService;
import org.peachSpring.app.services.UserService;
import org.peachSpring.app.util.constants.Gender;
import org.peachSpring.app.util.search_config.UserSearchConfig;
import org.peachSpring.app.util.search_config.constants.UserFilter;
import org.peachSpring.app.util.validators.UserOfflineValidator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


import java.util.Arrays;

@Controller
@RequestMapping("/users")
public class UsersController {

    private final UserOfflineValidator userOfflineValidator;
    private final UserService userService;
    private final BooksUsersService booksUsersService;
    private final BookService bookService;

    public UsersController(UserOfflineValidator userOfflineValidator, UserService userService, BooksUsersService booksUsersService, BookService bookService) {

        this.userOfflineValidator = userOfflineValidator;
        this.userService = userService;
        this.booksUsersService = booksUsersService;
        this.bookService = bookService;
    }

    @GetMapping()
    public String index(HttpServletRequest httpServletRequest,
                        Model model,
                        @ModelAttribute("searchConfig") UserSearchConfig searchConfig){

        int numberOfPage = 0;
        try {
            numberOfPage = Integer.parseInt(httpServletRequest.getParameter("page"));
            if (numberOfPage<0){
                numberOfPage = 0;
            }
        } catch (NumberFormatException ignore) {}
        User curUser = UserService.getCurrentUsersPrinciples();
        searchConfig.setNumberOfPage(numberOfPage);
        model.addAttribute("filters", UserFilter.values());
        model.addAttribute("users", userService.findAll(searchConfig));
        model.addAttribute("numberOfPage", numberOfPage);
        model.addAttribute("stringToFind", searchConfig.getStringToFind());
        model.addAttribute("filter",searchConfig.getFilter());
        model.addAttribute("usersName", curUser.getName());
        return "users/index";
    }
    @GetMapping("/{id}")
    public String show(@PathVariable("id") long id, Model model){
        try {
            User curUser = userService.findOne(id);
            User principles = UserService.getCurrentUsersPrinciples();
            model.addAttribute("id",id);
            model.addAttribute("user", curUser);
            model.addAttribute("usersRole", principles.getRole());
            model.addAttribute("usersName", principles.getName());
        } catch (UserNotFoundException e) {
            e.printStackTrace();
            return "errors/userNotFound";
        }
        return "users/user";
    }


    @GetMapping("/new")
    public String requestToAddNewUser(Model model,
                                      @ModelAttribute("newUser") User user){
        model.addAttribute("genders",Arrays.asList(Gender.MALE,Gender.FEMALE));

        return "users/new";
    }
    @PostMapping()
    public String createUser(   Model model,
                                @ModelAttribute("newUser")
                                 @Valid User user,
                                BindingResult bindingResult){
        userOfflineValidator.validate(user,bindingResult);
        if (bindingResult.hasErrors()){
            model.addAttribute("genders",Arrays.asList(Gender.values()));
            return "users/new";
        }
        userService.save(user);
        return "redirect:/users";
    }
    @GetMapping("/{id}/edit")
    public String requestToEditUser(Model model, @PathVariable("id") long id){
        User principles = UserService.getCurrentUsersPrinciples();
        model.addAttribute("usersRole", principles.getRole());
        model.addAttribute("usersName", principles.getName());
        model.addAttribute("genders",Arrays.asList(Gender.MALE,Gender.FEMALE));
        model.addAttribute("curUser", userService.findOne(id));
        return "users/edit";
    }
    @PatchMapping("/{id}")
    public String editUser(Model model,
                            @ModelAttribute("curUser") @Valid User user,
                           BindingResult bindingResult,
                           @PathVariable("id") long id){
        userOfflineValidator.validate(user,bindingResult);
        model.addAttribute("genders",Arrays.asList(Gender.MALE,Gender.FEMALE));
        if (bindingResult.hasErrors()){
            return "users/edit";
        }
        userService.update(id,user);

        return "redirect:/users";
    }
    @PatchMapping("/ban/{id}")
    public String banUser(Model model,
                          @PathVariable("id") long id){
        userService.changeLocking(id);
        return "redirect:/users/{id}";
    }
    @PatchMapping("/unban/{id}")
    public String unbanUser(Model model,
                          @PathVariable("id") long id){
        userService.changeLocking(id);
        return "redirect:/users/{id}";
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable("id") long id){
        try {
            userService.deleteById(id);
        } catch (CannotDeleteUserException e) {
            e.printStackTrace();
            return "errors/cannotDeleteUser";
        }
        return "redirect:/users";
    }











}
