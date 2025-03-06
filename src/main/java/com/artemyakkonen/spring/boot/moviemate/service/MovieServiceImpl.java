package com.artemyakkonen.spring.boot.moviemate.service;

import com.artemyakkonen.spring.boot.moviemate.dto.MovieDTO;
import com.artemyakkonen.spring.boot.moviemate.repository.MovieRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieServiceImpl implements MovieService{

    private final MovieRepository movieRepository;

    public MovieServiceImpl(MovieRepository movieRepository){
        this.movieRepository = movieRepository;
    }

    @Override
    public MovieDTO getMovieById(Long id) {
        return null;
    }

    @Override
    public MovieDTO createMovie(MovieDTO movieDTO) {
        return null;
    }

    @Override
    public List<MovieDTO> getAllMovies() {
        return null;
    }

    @Override
    public MovieDTO updateMovie(Long id, MovieDTO movieDTO) {
        return null;
    }

    @Override
    public void deleteMovie(Long id) {

    }
}
