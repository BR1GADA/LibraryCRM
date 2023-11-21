package org.peachSpring.app.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.peachSpring.app.models.User;
import org.peachSpring.app.security.UsersDetails;
import org.peachSpring.app.services.BookService;
import org.peachSpring.app.services.UserService;
import org.peachSpring.app.util.search_config.BookSearchConfig;
import org.peachSpring.app.util.search_config.constants.BookFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class MainController {
    private final BookService bookService;
    @Autowired
    public MainController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/")
    public String showMainPage(HttpServletRequest httpServletRequest,
                               Model model,
                               @ModelAttribute("searchConfig") BookSearchConfig searchConfig){
        int numberOfPage = 0;
        try {
            numberOfPage = Integer.parseInt(httpServletRequest.getParameter("page"));
            if (numberOfPage<0){
                numberOfPage = 0;
            }
        } catch (NumberFormatException ignore) {}
        User curUser = UserService.getCurrentUsersPrinciples();
        searchConfig.setNumberOfPage(numberOfPage);
        searchConfig.setFilter(BookFilter.IS_NOT_APPROVED);
        model.addAttribute("usersRole", curUser.getRole());
        model.addAttribute("usersName", curUser.getName());
        model.addAttribute("usersId", curUser.getId());
        model.addAttribute("numberOfPage", numberOfPage);
        model.addAttribute("allBooks", bookService.getBooks(searchConfig));
        return "main/main";
    }

}
