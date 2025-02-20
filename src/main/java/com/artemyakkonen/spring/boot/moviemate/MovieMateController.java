package com.artemyakkonen.spring.boot.moviemate;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/movies")
public class MovieMateController {
    private final MovieMateRepository movieMateRepository;

    @Autowired
    public MovieMateController(MovieMateRepository movieMateRepository){
        this.movieMateRepository = movieMateRepository;
    }

    @GetMapping("/{requestedId}")
    private ResponseEntity<Movie> findById(@PathVariable Long requestedId){
        Optional movieOptional = movieMateRepository.findById(requestedId);
        if(movieOptional.isPresent()){
            return ResponseEntity.ok((Movie) movieOptional.get());
        }else{
        return ResponseEntity.notFound().build();
        }
    }

}
