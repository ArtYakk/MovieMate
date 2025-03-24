package com.artemyakkonen.spring.boot.moviemate.controller;

import com.artemyakkonen.spring.boot.moviemate.dto.MovieDTO;
import com.artemyakkonen.spring.boot.moviemate.entity.Movie;
import com.artemyakkonen.spring.boot.moviemate.service.MovieService;
import com.artemyakkonen.spring.boot.moviemate.util.AnsiColors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ansi.AnsiBackground;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import java.net.URI;
import java.security.Principal;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/")
public class MovieController {
    private final MovieService movieService;

    @Autowired
    public MovieController(MovieService movieService){
        this.movieService = movieService;
    }

    //@PreAuthorize("hasRole('ALL')")
    @GetMapping("/welcome")
    public String welcome(){
        return "This is unprotected WELCOME page";
    }

    @GetMapping("/users")
    public String pageForUser(){
        return "This page is only for users";
    }

    @GetMapping("/admins")
    public String pageForAdmin(){
        return "This page is only for admins";
    }

    @GetMapping("/all")
    public String pageForAll(){
        return "This page is only for all employees";
    }

    //----------------------------------------------------------------------------------------------------------//

    @GetMapping("/movies/{requestedId}")
    private ResponseEntity<MovieDTO> findById(@PathVariable Long requestedId){
        MovieDTO movieDTO = movieService.getMovieById(requestedId);
        if(movieDTO == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(movieDTO);
    }

    @PostMapping("/movies")
    private ResponseEntity<Void> addNewFilm(@RequestBody Movie newMovie, UriComponentsBuilder ucb, Principal principal){
        newMovie.setAddedBy(principal.getName());
        log.info(AnsiColors.blackOnBlue(principal.getName()));
        URI locationOfNewFilm = movieService.createMovie(newMovie, ucb);
        return ResponseEntity.created(locationOfNewFilm).build();
    }

    //@PreAuthorize("hasAuthority('ROLE_USER')")
    @GetMapping("/movies")
    private ResponseEntity<List<MovieDTO>> findAllMovies(Pageable pageable){
        List<MovieDTO> page = movieService.findAllMovies(
                PageRequest.of(
                        pageable.getPageNumber(),
                        pageable.getPageSize(),
                        pageable.getSort()
                )
        );
                return ResponseEntity.ok(page);
    }



}
