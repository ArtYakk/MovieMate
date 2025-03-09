package com.artemyakkonen.spring.boot.moviemate.mapper;

import com.artemyakkonen.spring.boot.moviemate.dto.MovieDTO;
import com.artemyakkonen.spring.boot.moviemate.entity.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MovieMapper {
    private final ReviewMapper reviewMapper;

    @Autowired
    public MovieMapper(ReviewMapper reviewMapper){
        this.reviewMapper = reviewMapper;
    }

    public MovieDTO toMovieDTO(Movie movie) {
        return MovieDTO.builder()
                .id(movie.getId())
                .title(movie.getTitle())
                .director(movie.getDirector())
                .genre(movie.getGenre())
                .year(movie.getYear())
                .description(movie.getDescription())
                .reviews(reviewMapper.toReviewDTOList(movie.getReviews()))
                .build();
    }

    public Movie toMovie(MovieDTO movieDTO) {
        return Movie.builder()
                .id(movieDTO.getId())
                .title(movieDTO.getTitle())
                .director(movieDTO.getDirector())
                .genre(movieDTO.getGenre())
                .year(movieDTO.getYear())
                .description(movieDTO.getDescription())
                .reviews(reviewMapper.toReviewList(movieDTO.getReviews()))
                .build();
    }

    public List<MovieDTO> toMovieDTOList(List<Movie> movies) {
        return movies.stream()
                .map(this::toMovieDTO)
                .toList();
    }

    public List<Movie> toMovieList(List<MovieDTO> movies) {
        return movies.stream()
                .map(this::toMovie)
                .toList();
    }
}
