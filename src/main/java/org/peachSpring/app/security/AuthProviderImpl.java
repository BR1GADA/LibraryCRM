package org.peachSpring.app.security;

import org.peachSpring.app.services.UsersDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class AuthProviderImpl implements AuthenticationProvider {

    private final UsersDetailsService usersDetailsService;
    private final PasswordEncoderImpl passwordEncoder;
    @Autowired
    public AuthProviderImpl(UsersDetailsService usersDetailsService, PasswordEncoderImpl passwordEncoder) {
        this.usersDetailsService = usersDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String login = authentication.getName();
        String password = authentication.getCredentials().toString();
        UserDetails curUser = usersDetailsService.loadUserByUsername(login);
        System.out.println(passwordEncoder.encode("rar"));
        System.out.println(passwordEncoder.encode("qqq"));
        if (!passwordEncoder.matches(password,curUser.getPassword())){
            throw new BadCredentialsException("Password is incorrect");
        }
        return new UsernamePasswordAuthenticationToken(curUser,
                password,
                curUser.getAuthorities());

    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}
