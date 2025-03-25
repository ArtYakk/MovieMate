package com.artemyakkonen.spring.boot.moviemate.service.impl;

import com.artemyakkonen.spring.boot.moviemate.dto.MovieDTO;
import com.artemyakkonen.spring.boot.moviemate.entity.Movie;
import com.artemyakkonen.spring.boot.moviemate.mapper.MovieMapper;
import com.artemyakkonen.spring.boot.moviemate.repository.MovieRepository;
import com.artemyakkonen.spring.boot.moviemate.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Service
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;
    private final MovieMapper movieMapper;

    @Autowired
    public MovieServiceImpl(MovieRepository movieRepository, MovieMapper movieMapper){
        this.movieRepository = movieRepository;
        this.movieMapper = movieMapper;
    }

    @Override
    public MovieDTO getMovieById(Long id) {
        Optional<Movie> movieOptional = movieRepository.findById(id);
        return movieOptional.map(movieMapper::toMovieDTO).orElse(null);
    }

    @Override
    public URI createMovie(Movie newMovie, UriComponentsBuilder ucb) {
        Movie savedMovie = movieRepository.save(newMovie);
        return ucb
                .path("/movies/{id}")
                .buildAndExpand(savedMovie.getId())
                .toUri();
    }

    @Override
    public List<MovieDTO> findAllMovies() {
        return StreamSupport.stream(movieRepository.findAll().spliterator(), false)
                .map(movieMapper::toMovieDTO)
                .toList();
    }

    @Override
    public List<MovieDTO> findAllMovies(Pageable pageable){
        return movieMapper.toMovieDTOList(movieRepository.findAll(pageable).getContent());
    }

    @Override
    public MovieDTO updateMovie(Long id, MovieDTO movieDTO) {
        movieRepository.save(movieMapper.toMovie(movieDTO));
        return movieRepository.findById(id).map(movieMapper::toMovieDTO).orElse(null);
    }

    @Override
    public void deleteMovie(Long id) {
        movieRepository.deleteById(id);
    }
}
