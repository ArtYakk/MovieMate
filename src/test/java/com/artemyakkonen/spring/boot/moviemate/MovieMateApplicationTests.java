package com.artemyakkonen.spring.boot.moviemate;

import com.artemyakkonen.spring.boot.moviemate.entity.Movie;
import com.artemyakkonen.spring.boot.moviemate.entity.Review;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;

import net.minidev.json.JSONArray;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;


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
        assertThat(title).isEqualTo("Inception");

    }

    @Test
    void shouldNotReturnMovieWithUnknownId() {
        ResponseEntity<String> response = restTemplate.getForEntity("/movies/1000", String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isBlank();

    }

    @Test
    @Sql(scripts = "cleanup.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
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
    void shouldReturnReturnAllMoviesWhenListIsRequested() throws JSONException {
        ResponseEntity<String> response = restTemplate.getForEntity("/movies", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        DocumentContext documentContext = JsonPath.parse(response.getBody());
        int movieCount = documentContext.read("$.length()");
        assertThat(movieCount).isEqualTo(5);

        JSONArray idJsonArray = documentContext.read("$..id");
        List<Long> idList = new ArrayList<>();
        for(int i=0; i<idJsonArray.size(); i++){
            idList.add(Long.valueOf((Integer)idJsonArray.get(i)));
        }
        assertThat(idList.size()).isEqualTo(5);
    }

    @Test
    void shouldReturnAPageOfMovies(){
        ResponseEntity<String> response = restTemplate.getForEntity("/movies?page=0&size=1", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        DocumentContext documentContext = JsonPath.parse(response.getBody());
        JSONArray page = documentContext.read("$[*]");
        assertThat(page.size()).isEqualTo(1);
    }

    @Test
    void shouldReturnASortedPageOfMovies(){
        ResponseEntity<String> response = restTemplate.getForEntity("/movies?page=0&size=1&sort=year,desc", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        DocumentContext documentContext = JsonPath.parse(response.getBody());
        JSONArray page = documentContext.read("$[*]");
        assertThat(page.size()).isEqualTo(1);

        Number year = documentContext.read("$[0].year");
        assertThat(year).isEqualTo(2014);

    }

    @Test
    void shouldReturnASortedPageOfMoviesWithNoParametersAndUseDefaultValues(){
        ResponseEntity<String> response = restTemplate.getForEntity("/movies", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        DocumentContext documentContext = JsonPath.parse(response.getBody());
        JSONArray page = documentContext.read("$[*]");

        assertThat(page.size()).isEqualTo(5);
    }


}
