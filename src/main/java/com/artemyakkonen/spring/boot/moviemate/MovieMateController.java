package com.artemyakkonen.spring.boot.moviemate;

import com.artemyakkonen.spring.boot.moviemate.dto.MovieDTO;
import com.artemyakkonen.spring.boot.moviemate.dto.ReviewDTO;
import com.artemyakkonen.spring.boot.moviemate.entity.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

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

          MovieDTO movieDTO = MovieDTO.builder()
                  .id(movie.getId())
                  .title(movie.getTitle())
                  .director(movie.getDirector())
                  .genre(movie.getGenre())
                  .year(movie.getYear())
                  .description(movie.getDescription())
                  .build();

          List<ReviewDTO> reviewDTOList = movie.getReviews().stream()
                  .map(review -> {
                      return ReviewDTO.builder()
                              .review_id(review.getReview_id())
                              .review_author(review.getReview_author())
                              .rating(review.getRating())
                              .content(review.getContent())
                              .build();
                  })
                  .toList();

          movieDTO.setReviews(reviewDTOList);
          return ResponseEntity.ok(movieDTO);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/")
    private ResponseEntity<Void> addNewFilm(@RequestBody Movie newMovie, UriComponentsBuilder ucb){
        Movie savedMovie = movieMateRepository.save(newMovie);
        URI locationOfNewCashCard = ucb
                .path("/movies/{id}")
                .buildAndExpand(savedMovie.getId())
                .toUri();
        return ResponseEntity.created(locationOfNewCashCard).build();
    }

}
