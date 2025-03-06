package com.artemyakkonen.spring.boot.moviemate.service;

import com.artemyakkonen.spring.boot.moviemate.dto.MovieDTO;
import com.artemyakkonen.spring.boot.moviemate.repository.MovieRepository;
import org.springframework.stereotype.Service;

import java.util.List;

public interface MovieService {
   MovieDTO getMovieById(Long id);
   List<MovieDTO> getAllMovies();
   MovieDTO createMovie(MovieDTO movieDTO);
   MovieDTO updateMovie(Long id, MovieDTO movieDTO);
   void deleteMovie(Long id);
}
