package org.peachSpring.app.util.validators;

import org.peachSpring.app.models.User;
import org.peachSpring.app.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;

@Component
public class UserOfflineValidator implements Validator {

    private final UserService usersService;

    @Autowired
    public UserOfflineValidator(UserService usersService) {
        this.usersService = usersService;

    }

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User curUser = (User) target;
        Optional<User> userToFind = usersService.findOneByNameIgnoreCase(curUser.getName());
        if (userToFind.isPresent()){
            if (userToFind.get().getId() != curUser.getId()) {
                errors.rejectValue("name","","This name is already exists");
            }
        }
    }
}
