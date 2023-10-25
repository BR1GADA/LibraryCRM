package org.peachSpring.app.security;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
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
        http.authorizeRequests()
                .requestMatchers("/auth/login","/styles/styleForAuth.css")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .formLogin(form->form.loginPage("/auth/login").loginProcessingUrl("/login").defaultSuccessUrl("/")

                 );

        return http.build();
    }

    @Bean
    public PasswordEncoder getPasswordEncoder(){
        return NoOpPasswordEncoder.getInstance();
    }
}
