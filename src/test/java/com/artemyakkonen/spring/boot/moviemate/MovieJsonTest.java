package com.artemyakkonen.spring.boot.moviemate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
public class MovieJsonTest {

    private final JacksonTester<Movie> json;

    @Autowired
    public MovieJsonTest(JacksonTester<Movie> json){
        this.json = json;
    }

    @Test
    void myFirstTest(){
        assertThat(12).isEqualTo(12);
    }

    @Test
    void MovieSerializationTest() throws IOException {
        Movie movie = new Movie(1, "War and Peace", "Lev Tolstoy", "Novel", 1867, "a long story about war and peace");
        assertThat(json.write(movie)).isStrictlyEqualToJson("expected.json");
    }


}
