package com.artemyakkonen.spring.boot.moviemate.service;

import com.artemyakkonen.spring.boot.moviemate.dto.MovieDTO;
import com.artemyakkonen.spring.boot.moviemate.entity.Movie;
import com.artemyakkonen.spring.boot.moviemate.repository.MovieRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

public interface MovieService {
   MovieDTO getMovieById(Long id);
   URI createMovie(Movie movie, UriComponentsBuilder ucb);
   List<MovieDTO> findAllMovies();
   MovieDTO updateMovie(Long id, MovieDTO movieDTO);
   void deleteMovie(Long id);
}
