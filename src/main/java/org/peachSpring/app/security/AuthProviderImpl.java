package org.peachSpring.app.security;

import org.peachSpring.app.services.UsersDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class AuthProviderImpl implements AuthenticationProvider {

    private final UsersDetailsService usersDetailsService;
    private final PasswordEncoderImpl passwordEncoder;
    private final AccountStatusUserDetailsChecker accountStatusUserDetailsChecker;
    @Autowired
    public AuthProviderImpl(UsersDetailsService usersDetailsService, PasswordEncoderImpl passwordEncoder) {
        this.usersDetailsService = usersDetailsService;
        this.passwordEncoder = passwordEncoder;
        this.accountStatusUserDetailsChecker = new AccountStatusUserDetailsChecker();
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String login = authentication.getName();
        String password = authentication.getCredentials().toString();
        UserDetails curUser = usersDetailsService.loadUserByUsername(login);
        try {
            accountStatusUserDetailsChecker.check(curUser);
        } catch (LockedException e) {
            throw new LockedException("User account is locked");
        }
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
