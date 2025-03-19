package com.artemyakkonen.spring.boot.moviemate.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import javax.management.relation.Role;

@Configuration
@EnableWebSecurity // SecurityFilterChain
        //@EnableMethodSecurity // Безопасность на уровне методов @PreAuthorize, @PostAuthorize, @Secured и @RolesAllowed
public class SecurityConfig {


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/welcome").permitAll() // Без авторизации
//                        .requestMatchers("/admin").hasRole("ADMIN")
                        .requestMatchers("/movies/**").hasAuthority("USER")
                        .anyRequest().authenticated() // аналог .requestMatchers("/**").authenticated()
                )
                .formLogin(Customizer.withDefaults())
                .logout(LogoutConfigurer::permitAll)
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
