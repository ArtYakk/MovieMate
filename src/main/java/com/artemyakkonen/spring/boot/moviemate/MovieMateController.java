package com.artemyakkonen.spring.boot.moviemate;


import com.artemyakkonen.spring.boot.moviemate.dto.MovieDTO;
import com.artemyakkonen.spring.boot.moviemate.dto.ReviewDTO;
import com.artemyakkonen.spring.boot.moviemate.entity.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/movies")
public class MovieMateController {
    private final MovieMateRepository movieMateRepository;

    @Autowired
    public MovieMateController(MovieMateRepository movieMateRepository){
        this.movieMateRepository = movieMateRepository;
    }

    @GetMapping("/{requestedId}")
    private ResponseEntity<MovieDTO> findById(@PathVariable Long requestedId){
        Optional<Movie> movieOptional = movieMateRepository.findById(requestedId);
        if(movieOptional.isPresent()){
          Movie movie = movieOptional.get();
          MovieDTO movieDTO = new MovieDTO();
          movieDTO.setId(movie.getId());
          movieDTO.setTitle(movie.getTitle());
          movieDTO.setDirector(movie.getDirector());
          movieDTO.setGenre(movie.getGenre());
          movieDTO.setYear(movie.getYear());
          movieDTO.setDescription(movie.getDescription());

          List<ReviewDTO> reviewDTOList = movie.getReviews().stream()
                  .map(review -> {
                      ReviewDTO reviewDTO = new ReviewDTO();
                      reviewDTO.setReview_id(review.getReview_id());
                      reviewDTO.setReview_author(review.getReview_author());
                      reviewDTO.setRating(review.getRating());
                      reviewDTO.setContent(review.getContent());
                      return reviewDTO;
                  })
                  .toList();
          movieDTO.setReviews(reviewDTOList);

          return ResponseEntity.ok(movieDTO);

        }else{
        return ResponseEntity.notFound().build();
        }
    }

}
