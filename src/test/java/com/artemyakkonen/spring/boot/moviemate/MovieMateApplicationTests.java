package com.artemyakkonen.spring.boot.moviemate;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

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
        ResponseEntity<String> response = restTemplate.getForEntity("/movies/21", String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        DocumentContext documentContext = JsonPath.parse(response.getBody());

        Number id = documentContext.read("$.id");
        assertThat(id).isNotNull();
        assertThat(id).isEqualTo(21);

        String title = documentContext.read("$.title");
        assertThat(title).isEqualTo("The LOR");

    }

    @Test
    void shouldNotReturnMovieWithUnknownId() {
        ResponseEntity<String> response = restTemplate.getForEntity("/movies/1000", String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isBlank();

    }

}
