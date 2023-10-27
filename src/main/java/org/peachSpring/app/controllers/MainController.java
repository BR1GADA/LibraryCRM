package org.peachSpring.app.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {
    @GetMapping("/")
    public String showMainPage(){
        return "main/main";
    }
    @GetMapping("/error404")
    public String showErrorPage(){
        return "errors/errorPage";
    }
}
