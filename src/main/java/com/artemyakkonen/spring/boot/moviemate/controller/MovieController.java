package com.artemyakkonen.spring.boot.moviemate.controller;

import com.artemyakkonen.spring.boot.moviemate.dto.MovieDTO;
import com.artemyakkonen.spring.boot.moviemate.dto.ReviewDTO;
import com.artemyakkonen.spring.boot.moviemate.entity.Movie;
import com.artemyakkonen.spring.boot.moviemate.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/")
public class MovieController {
    private final MovieService movieService;

    @Autowired
    public MovieController(MovieService movieService){
        this.movieService = movieService;
    }

    @GetMapping("/movies/{requestedId}")
    private ResponseEntity<MovieDTO> findById(@PathVariable Long requestedId){
        MovieDTO movieDTO = movieService.getMovieById(requestedId);
        if(movieDTO == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(movieDTO);
    }

    @PostMapping("/movies")
    private ResponseEntity<Void> addNewFilm(@RequestBody Movie newMovie, UriComponentsBuilder ucb){
        URI locationOfNewFilm = movieService.createMovie(newMovie, ucb);
        return ResponseEntity.created(locationOfNewFilm).build();
    }

    @GetMapping("/movies")
    private ResponseEntity<List<MovieDTO>> findAllMovies(){
       List<MovieDTO> movieDTOList = movieService.findAllMovies();

        return ResponseEntity.ok(movieDTOList);
    }

}
