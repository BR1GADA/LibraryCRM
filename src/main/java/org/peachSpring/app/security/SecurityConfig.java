package org.peachSpring.app.security;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.RegexRequestMatcher;
import org.springframework.stereotype.Component;

@EnableWebSecurity
@Component
public class SecurityConfig  {
    private final AuthProviderImpl authProvider;

    @Autowired
    public SecurityConfig(AuthProviderImpl authProvider) {
        this.authProvider = authProvider;

    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((authorize)->authorize.requestMatchers(
                        "/auth/login",
                                "/styles/styleForAuth.css",
                                "/styles/styleForRegistration.css",
                                "/auth/registration",
                                "/error")
                        .permitAll()
                        .requestMatchers(
                                new RegexRequestMatcher("/", "GET"),
                                new RegexRequestMatcher("/styles/.*", "GET"),
                                new RegexRequestMatcher("/books.*", "GET"),
                                new RegexRequestMatcher("/\\?continue", "GET"),
                                new RegexRequestMatcher("/images/.*", "GET"),
                                new RegexRequestMatcher("/js/.*", "GET"),
                                new RegexRequestMatcher("/fonts/.*", "GET"),
                                new RegexRequestMatcher("/favicon/.*","GET"))
                        .authenticated()
                        .anyRequest()
                        .hasRole("ADMIN"))

                .formLogin(form->form.loginPage("/auth/login")
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/")
                        .failureUrl("/auth/login?error")
                 )
                .logout(logout->logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/auth/login"));

        return http.build();
    }


}
