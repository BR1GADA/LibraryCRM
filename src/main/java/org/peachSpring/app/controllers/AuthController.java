package org.peachSpring.app.controllers;

import jakarta.validation.Valid;
import org.peachSpring.app.models.User;
import org.peachSpring.app.security.PasswordEncoderImpl;
import org.peachSpring.app.services.UserService;
import org.peachSpring.app.util.constants.Gender;

import org.peachSpring.app.util.validators.UserOnlineValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/auth")
public class AuthController {
    private final UserOnlineValidator userValidator;
    private final UserService userService;
    private final PasswordEncoderImpl passwordEncoder;

    public AuthController(UserOnlineValidator userValidator, UserService userService, PasswordEncoderImpl passwordEncoder) {
        this.userValidator = userValidator;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/login")
    public String showLoginPage(){
        return "auth/login";
    }
    @GetMapping("/registration")
    public String showRegPage(Model model,
                              @ModelAttribute("curUser")User user,
                              @ModelAttribute("passwordAgain")String passwordAgain){
        model.addAttribute("genders",Gender.values());
        return "auth/registration";
    }
    @PostMapping("/registration")
    public String regUser(Model model,
                          @RequestParam(value = "passwordConfirm") String passwordAgain,
                          @ModelAttribute ("curUser")@Valid User user,
                          BindingResult bindingResult){
        userValidator.validate(user,bindingResult);
        if (bindingResult.hasErrors()){
            model.addAttribute("genders",Gender.values());
            return "auth/registration";
        }

        if (!passwordAgain.equals(user.getPassword())){
            model.addAttribute("genders",Gender.values());
            bindingResult.rejectValue("password","","Passwords should be equal");
            return "auth/registration";
        }
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        user.setRole("ROLE_USER");
        userService.save(user);
        return "redirect:/auth/login";


    }

}
