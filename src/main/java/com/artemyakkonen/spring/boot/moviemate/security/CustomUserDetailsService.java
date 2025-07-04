package com.artemyakkonen.spring.boot.moviemate.security;

import ch.qos.logback.classic.boolex.MarkerList;
import com.artemyakkonen.spring.boot.moviemate.entity.User;
import com.artemyakkonen.spring.boot.moviemate.repository.UserRepository;
import com.artemyakkonen.spring.boot.moviemate.util.AnsiColors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ansi.AnsiBackground;
import org.springframework.boot.ansi.AnsiColor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public CustomUserDetailsService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByName(username);
        CustomUserDetails customUserDetails =  user.map(CustomUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException(username + "There is no such user in the repository"));
        return customUserDetails;
    }
}
