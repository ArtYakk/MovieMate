package com.artemyakkonen.spring.boot.moviemate;

import com.artemyakkonen.spring.boot.moviemate.entity.Movie;
import com.artemyakkonen.spring.boot.moviemate.entity.Review;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MovieMateApplicationTests {

    TestRestTemplate restTemplate;

    @Autowired
    public MovieMateApplicationTests(TestRestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }

    @Test
    void shouldReturnMovieWhenDataIsSaved() {
        ResponseEntity<String> response = restTemplate.getForEntity("/movies/1", String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        DocumentContext documentContext = JsonPath.parse(response.getBody());

        Number id = documentContext.read("$.id");
        assertThat(id).isNotNull();
        assertThat(id).isEqualTo(1);

        String title = documentContext.read("$.title");
        assertThat(title).isEqualTo("Hencock");

    }

    @Test
    void shouldNotReturnMovieWithUnknownId() {
        ResponseEntity<String> response = restTemplate.getForEntity("/movies/1000", String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isBlank();

    }

    @Test
    void shouldAddNewFilm(){
        List<Review> reviews = new ArrayList<>();

        reviews.add(Review.builder()
                .review_author("Autest1")
                .rating(8L)
                .content("ts1")
                .build());
        reviews.add(Review.builder()
                .review_author("Autest2")
                .rating(9L)
                .content("ts2")
                .build());
        reviews.add(Review.builder()
                .review_author("Autest3")
                .rating(10L)
                .content("ts3")
                .build());

        Movie movie = Movie.builder()
                .title("Titest")
                .director("Ditest")
                .genre("Getest")
                .year(1500L)
                .description("Detest")
                .reviews(reviews)
                .build();

        ResponseEntity<Void> postResponse = restTemplate.postForEntity("/movies", movie, Void.class);
        assertThat(postResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        URI locationOfNewFilm = postResponse.getHeaders().getLocation();
        ResponseEntity<String> getResponse = restTemplate.getForEntity(locationOfNewFilm, String.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        DocumentContext documentContext = JsonPath.parse(getResponse.getBody());
        Number id = documentContext.read("$.id");
        Number year = documentContext.read("$.year");

        assertThat(id).isNotNull();
        assertThat(year).isEqualTo(1500);
    }

    @Test
    void shouldReturnReturnAllMoviesWhenListIsRequested(){
        ResponseEntity<String> response = restTemplate.getForEntity("/movies", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

    }


}
