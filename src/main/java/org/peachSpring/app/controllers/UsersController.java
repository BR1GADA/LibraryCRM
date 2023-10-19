package org.peachSpring.app.controllers;

import jakarta.validation.Valid;
import org.peachSpring.app.exceptions.CannotDeleteUserException;
import org.peachSpring.app.exceptions.UserNotFoundException;
import org.peachSpring.app.models.User;
import org.peachSpring.app.services.UserService;
import org.peachSpring.app.util.constants.Gender;
import org.peachSpring.app.util.validators.UserValidator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


import java.util.Arrays;

@Controller
@RequestMapping("/users")
public class UsersController {

    private final UserValidator userValidator;
    private final UserService userService;

    public UsersController(UserValidator userValidator, UserService userService) {

        this.userValidator = userValidator;
        this.userService = userService;
    }

    @GetMapping()
    public String index(Model model){
        model.addAttribute("users", userService.findAll());

        return "users/index";
    }
    @GetMapping("/{id}")
    public String show(@PathVariable("id") long id, Model model){
        try {
            model.addAttribute("id",id);
            model.addAttribute("user", userService.findOne(id));
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
        userValidator.validate(user,bindingResult);
        if (bindingResult.hasErrors()){
            model.addAttribute("genders",Arrays.asList(Gender.values()));
            return "users/new";
        }
        userService.save(user);
        return "redirect:/users";
    }
    @GetMapping("/{id}/edit")
    public String requestToEditUser(Model model, @PathVariable("id") long id){
        model.addAttribute("genders",Arrays.asList(Gender.MALE,Gender.FEMALE));
        model.addAttribute("curUser", userService.findOne(id));
        return "users/edit";
    }
    @PatchMapping("/{id}")
    public String editUser(Model model,
                            @ModelAttribute("curUser") @Valid User user,
                           BindingResult bindingResult,
                           @PathVariable("id") long id){
        userValidator.validate(user,bindingResult);
        model.addAttribute("genders",Arrays.asList(Gender.MALE,Gender.FEMALE));
        if (bindingResult.hasErrors()){
            return "users/edit";
        }
        userService.update(id,user);

        return "redirect:/users";
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
