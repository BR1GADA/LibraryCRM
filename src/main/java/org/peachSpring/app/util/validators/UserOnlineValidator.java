package org.peachSpring.app.util.validators;

import org.peachSpring.app.models.User;
import org.peachSpring.app.services.UserService;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;
@Component
public class UserOnlineValidator implements Validator {
    private final UserService userService;

    public UserOnlineValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User curUser = (User) target;
        Optional<User> userToFind = userService.findOneByNameIgnoreCase(curUser.getName());
        if (userToFind.isPresent()){
            if (userToFind.get().getId() != curUser.getId()) {
                errors.rejectValue("name","","This name is already exists");
            }
        }
        if (curUser.getLogin()==null){
            errors.rejectValue("login","","Login shouldn`t be empty");
        } else {
            Optional<User> userToFindByLogin = userService.findOneByLogin(curUser.getLogin());
            if (userToFindByLogin.isPresent()){
                if (userToFindByLogin.get().getId() != curUser.getId()) {
                    errors.rejectValue("login","","This login is already exists");
                }
            }
        }
        if (curUser.getPassword() == null){
            errors.rejectValue("password","","Password shouldn`t be empty");
        }



    }
}
