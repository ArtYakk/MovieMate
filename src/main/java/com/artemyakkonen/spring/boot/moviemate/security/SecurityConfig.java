package com.artemyakkonen.spring.boot.moviemate.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
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
@EnableWebSecurity // SecurityFilterChain
@EnableMethodSecurity // Безопасность на уровне методов @PreAuthorize, @PostAuthorize, @Secured и @RolesAllowed
public class SecurityConfig {


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/welcome").permitAll() // Без авторизации
                        .requestMatchers("/users").hasRole("USER")
                        .requestMatchers("/admins").hasRole("ADMIN")
                        .requestMatchers("/all").hasAnyRole("USER", "ADMIN")

                        .requestMatchers(HttpMethod.GET,"/movies/{requestedId}").hasRole("USER") //Получить 1 фильм
                        .requestMatchers(HttpMethod.POST,"/movies").hasRole("ADMIN") //Добавить фильм
                        .requestMatchers(HttpMethod.GET,"/movies").hasAnyRole( "USER", "ADMIN") //Получить список фильмов
                        .anyRequest().authenticated() // аналог .requestMatchers("/**").authenticated()
                )
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(Customizer.withDefaults())
               // .formLogin(AbstractAuthenticationFilterConfigurer::permitAll)
               // .logout(LogoutConfigurer::permitAll)
//                .formLogin(form -> form
//                        .loginPage("/login")
//                        .defaultSuccessUrl("/home", true)
//                        .permitAll()
//                )
//                .logout(logout -> logout
//                                .logoutUrl("/logout")
//                                .logoutSuccessUrl("/login?logout")
//                                .permitAll()
//                        )
//                .exceptionHandling(ex -> ex
//                        .accessDeniedPage("/access-denied")
//                )
                .build();
    }


    @Bean
    PasswordEncoder passwordEncoder(){
       return new BCryptPasswordEncoder(5);
    }
}
