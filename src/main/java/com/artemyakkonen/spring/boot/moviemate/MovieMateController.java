package com.artemyakkonen.spring.boot.moviemate;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/movies")
public class MovieMateController {

    @GetMapping("/{requestedId}")
    private ResponseEntity<Movie> findById(@PathVariable Long requestedId){
        if(requestedId == 21){
            Movie movie = new Movie(21, "The LOR", "Peter Jackson", 2001);
            return ResponseEntity.ok(movie);
        }else{
        return ResponseEntity.notFound().build();
        }
    }

}
