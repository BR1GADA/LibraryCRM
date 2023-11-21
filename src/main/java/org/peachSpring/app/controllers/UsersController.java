package org.peachSpring.app.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
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
import org.peachSpring.app.util.validators.UserOnlineValidator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


import java.util.Arrays;

@Controller
@RequestMapping("/users")
public class UsersController {

    private final UserOnlineValidator userOnlineValidator;
    private final UserService userService;
    private final BooksUsersService booksUsersService;
    private final BookService bookService;

    public UsersController(UserOnlineValidator userOnlineValidator, UserService userService, BooksUsersService booksUsersService, BookService bookService) {
        this.userOnlineValidator = userOnlineValidator;


        this.userService = userService;
        this.booksUsersService = booksUsersService;
        this.bookService = bookService;
    }

    @GetMapping()
    public String index(HttpServletRequest httpServletRequest,
                        Model model,
                        @ModelAttribute("searchConfig") UserSearchConfig searchConfig) {

        int numberOfPage = 0;
        try {
            numberOfPage = Integer.parseInt(httpServletRequest.getParameter("page"));
            if (numberOfPage < 0) {
                numberOfPage = 0;
            }
        } catch (NumberFormatException ignore) {
        }
        User curUser = UserService.getCurrentUsersPrinciples();
        searchConfig.setNumberOfPage(numberOfPage);
        model.addAttribute("filters", UserFilter.values());
        model.addAttribute("users", userService.findAll(searchConfig));
        model.addAttribute("numberOfPage", numberOfPage);
        model.addAttribute("stringToFind", searchConfig.getStringToFind());
        model.addAttribute("filter", searchConfig.getFilter());
        model.addAttribute("usersName", curUser.getName());
        model.addAttribute("usersId", curUser.getId());
        return "users/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") long id, Model model) {
        try {
            User curUser = userService.findOne(id);
            User principles = UserService.getCurrentUsersPrinciples();
            model.addAttribute("id", id);
            model.addAttribute("user", curUser);
            model.addAttribute("usersRole", principles.getRole());
            model.addAttribute("usersName", principles.getName());
            model.addAttribute("usersId", principles.getId());
        } catch (UserNotFoundException e) {
            e.printStackTrace();
            return "errors/userNotFound";
        }
        return "users/user";
    }

    @GetMapping("/personal/{id}")
    public String showPersonalData(@PathVariable("id") long id, Model model) {
        User curUser = userService.findOne(id);
        User principles = UserService.getCurrentUsersPrinciples();
        try {
            model.addAttribute("id", id);
            model.addAttribute("user", curUser);
            model.addAttribute("usersRole", principles.getRole());
            model.addAttribute("usersName", principles.getName());
            model.addAttribute("usersId", principles.getId());
        } catch (UserNotFoundException e) {
            e.printStackTrace();
            return "errors/userNotFound";
        }
        if (id != principles.getId()) {
            return "redirect:/error";
        }
        return "users/user";
    }


    @GetMapping("/new")
    public String requestToAddNewUser(Model model,
                                      @ModelAttribute("newUser") User user) {
        model.addAttribute("genders", Arrays.asList(Gender.MALE, Gender.FEMALE));

        return "users/new";
    }

    @PostMapping()
    public String createUser(Model model,
                             @ModelAttribute("newUser")
                             @Valid User user,
                             BindingResult bindingResult) {
        userOnlineValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            model.addAttribute("genders", Arrays.asList(Gender.values()));
            return "users/new";
        }
        userService.save(user);
        return "redirect:/users";
    }

    @GetMapping("/{id}/edit")
    public String requestToEditUser(Model model, @PathVariable("id") long id) {
        User principles = UserService.getCurrentUsersPrinciples();
        if (principles.getRole().equals("ROLE_USER") && id != principles.getId()) {
            System.out.println(id + " " + principles.getId());
            return "redirect:/error";
        }

        model.addAttribute("usersRole", principles.getRole());
        model.addAttribute("usersName", principles.getName());
        model.addAttribute("genders", Arrays.asList(Gender.MALE, Gender.FEMALE));
        model.addAttribute("curUser", userService.findOne(id));
        model.addAttribute("correctUser", userService.findOne(id));
        model.addAttribute("usersId", principles.getId());
        return "users/edit";
    }

    @PatchMapping("/{id}")
    public String editUser(Model model,
                           @ModelAttribute("curUser") @Valid User user,
                           BindingResult bindingResult,
                           @PathVariable("id") long id) {
        User curUser = userService.findOne(id);
        user.setPassword(curUser.getPassword());
        user.setRole(curUser.getRole());
        User principles = UserService.getCurrentUsersPrinciples();
        userOnlineValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            model.addAttribute("correctUser", curUser);
            model.addAttribute("genders", Arrays.asList(Gender.MALE, Gender.FEMALE));
            model.addAttribute("usersRole", principles.getRole());
            model.addAttribute("usersName", principles.getName());
            model.addAttribute("usersId", principles.getId());
            return "users/edit";
        }
        if (principles.getRole().equals("ROLE_USER") && id != principles.getId()) {
            System.out.println(id + " " + principles.getId());
            return "redirect:/error";
        }

        userService.update(id, user);
        if (principles.getRole().equals("ROLE_USER")) {
            return "redirect:/users/personal/{id}";
        } else {
            return "redirect:/users/{id}";
        }

    }

    @PatchMapping("/ban/{id}")
    public String banUser(Model model,
                          @PathVariable("id") long id) {
        userService.changeLocking(id);
        return "redirect:/users/{id}";
    }

    @PatchMapping("/unban/{id}")
    public String unbanUser(Model model,
                            @PathVariable("id") long id) {
        userService.changeLocking(id);
        return "redirect:/users/{id}";
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable("id") long id,
                             HttpServletRequest httpServletRequest) {
        User principles = UserService.getCurrentUsersPrinciples();
        if (principles.getRole().equals("ROLE_USER") && id != principles.getId()) {
            System.out.println(id + " " + principles.getId());
            return "redirect:/error";
        }
        try {
            userService.deleteById(id);
        } catch (CannotDeleteUserException e) {
            e.printStackTrace();
            return "errors/cannotDeleteUser";
        }
        if (principles.getRole().equals("ROLE_USER")) {
            HttpSession session = httpServletRequest.getSession(false);
            if (session != null) {
                session.invalidate();
            }
            return "redirect:/auth/login";
        }
        return "redirect:/users";
    }


}
