package com.artemyakkonen.spring.boot.moviemate.controller;

import com.artemyakkonen.spring.boot.moviemate.dto.MovieDTO;
import com.artemyakkonen.spring.boot.moviemate.entity.Movie;
import com.artemyakkonen.spring.boot.moviemate.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/")
public class MovieController {
    private final MovieService movieService;

    @Autowired
    public MovieController(MovieService movieService){
        this.movieService = movieService;
    }

    @GetMapping("/welcome")
    public String welcome(){
        return "This is unprotected page";
    }

    @GetMapping("/users")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public String pageForUser(){
        return "This page is only for users";
    }

    @GetMapping("/admins")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String pageForAdmin(){
        return "This page is only for admins";
    }

    @GetMapping("/all")
    public String pageForAll(){
        return "This page is only for all employees";
    }

    /////////////////////////////////////////////////////////////////////

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

//    @GetMapping("/movies")
//    private ResponseEntity<List<MovieDTO>> findAllMovies(){
//       List<MovieDTO> movieDTOList = movieService.findAllMovies();
//
//        return ResponseEntity.ok(movieDTOList);
//    }

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
