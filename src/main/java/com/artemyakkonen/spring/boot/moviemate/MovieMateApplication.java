package com.artemyakkonen.spring.boot.moviemate;

import com.artemyakkonen.spring.boot.moviemate.util.AnsiColors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.ansi.AnsiBackground;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class MovieMateApplication {

    public static void main(String[] args) {
        SpringApplication.run(MovieMateApplication.class, args);
    }

}
