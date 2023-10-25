package org.peachSpring.app.services;

import org.peachSpring.app.models.User;
import org.peachSpring.app.repositories.UsersRepository;
import org.peachSpring.app.security.UsersDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsersDetailsService implements UserDetailsService {
    private final UsersRepository usersRepository;

    @Autowired
    public UsersDetailsService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> curUser = usersRepository.findByLogin(username);
        if (curUser.isEmpty()) {
            throw new UsernameNotFoundException("User doesn`t exist");
        } else {
            return new UsersDetails(curUser.get());
        }
    }
}
