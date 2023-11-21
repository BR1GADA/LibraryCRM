package org.peachSpring.app.security;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.RegexRequestMatcher;
import org.springframework.stereotype.Component;

@EnableWebSecurity
@Component
public class SecurityConfig  {
    private final AuthProviderImpl authProvider;
    private final CustomAuthenticationFailureHandler customAuthenticationFailureHandler;

    @Autowired
    public SecurityConfig(AuthProviderImpl authProvider, CustomAuthenticationFailureHandler customAuthenticationFailureHandler) {
        this.authProvider = authProvider;
        this.customAuthenticationFailureHandler = customAuthenticationFailureHandler;
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
                                new RegexRequestMatcher("/books.*", "POST"),
                                new RegexRequestMatcher("/\\?continue", "GET"),
                                new RegexRequestMatcher("/images/.*", "GET"),
                                new RegexRequestMatcher("/js/.*", "GET"),
                                new RegexRequestMatcher("/fonts/.*", "GET"),
                                new RegexRequestMatcher("/favicon/.*","GET"),
                                new RegexRequestMatcher("/users/personal/.*", "GET"),
                                new RegexRequestMatcher("/users/.*/edit.*", "GET"),
                                new RegexRequestMatcher("/users/.*", "PATCH"),
                                new RegexRequestMatcher("/users/.*","DELETE"))
                        .authenticated()
                        .anyRequest()
                        .hasRole("ADMIN"))

                .formLogin(form->form.loginPage("/auth/login")
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/")
                        //.failureUrl("/auth/login?error")
                        .failureHandler(customAuthenticationFailureHandler)
                 )
                .logout(logout->logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/auth/login"));

        return http.build();
    }


}
