package com.artemyakkonen.spring.boot.moviemate.config;

import com.artemyakkonen.spring.boot.moviemate.config.MyUserDetailsService;
import com.artemyakkonen.spring.boot.moviemate.service.impl.MovieServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Bean // Возвращаем кастомный MyUserDetailsService
    public MyUserDetailsService userDetailsService(){
        return new MyUserDetailsService();
    }

    @Bean
    public MovieServiceImpl movieService(){
        return new MovieServiceImpl();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService());
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http.authorizeHttpRequests(request -> request
//                .requestMatchers("/movies/**")
//                .authenticated())
//                .httpBasic(Customizer.withDefaults())
//                .csrf(csrf -> csrf.disable());

        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth.requestMatchers("/welcome").permitAll() // Без авторизации
                        .requestMatchers("/**").authenticated()) // С авторизацией и аутентификацией
                .formLogin(AbstractAuthenticationFilterConfigurer::permitAll)
                .build();


    }

    @Bean
    PasswordEncoder passwordEncoder(){
       return new BCryptPasswordEncoder(5);
    }
}
