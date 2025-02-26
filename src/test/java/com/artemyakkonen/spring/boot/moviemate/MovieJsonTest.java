package com.artemyakkonen.spring.boot.moviemate;

import com.artemyakkonen.spring.boot.moviemate.entity.Movie;
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
        Movie movie = new Movie(11L, "War and Peace", "Lev Tolstoy", 1867L);
        assertThat(json.write(movie)).isStrictlyEqualToJson("expected.json");

        assertThat(json.write(movie)).hasJsonPathNumberValue("@.id");
        assertThat(json.write(movie)).extractingJsonPathNumberValue("@.id").isEqualTo(11);

        assertThat(json.write(movie)).hasJsonPathStringValue("@.title");
        assertThat(json.write(movie)).extractingJsonPathStringValue("@.title").isEqualTo("War and Peace");

        assertThat(json.write(movie)).hasJsonPathStringValue("@.director");
        assertThat(json.write(movie)).extractingJsonPathStringValue("@.director").isEqualTo("Lev Tolstoy");

        assertThat(json.write(movie)).hasJsonPathNumberValue("@.year");
        assertThat(json.write(movie)).extractingJsonPathNumberValue("@.year").isEqualTo(1867);
    }

    @Test
    void MovieDeserializationTest(){
        String expected = """
                {
                  "id": 11,
                  "title": "War and Peace",
                  "director": "Lev Tolstoy",
                  "genre": null,
                  "year": 1867,
                  "description": null,
                  "reviews": null
                }
                """;
        try {
            assertThat(json.parse(expected)).isEqualTo(new Movie(11L, "War and Peace", "Lev Tolstoy", 1867L));
            assertThat(json.parseObject(expected).getId()).isEqualTo(11L);
            assertThat(json.parseObject(expected).getTitle()).isEqualTo("War and Peace");
            assertThat(json.parseObject(expected).getDirector()).isEqualTo("Lev Tolstoy");
            assertThat(json.parseObject(expected).getYear()).isEqualTo(1867L);
        } catch (IOException e) {
            System.out.println("Failed to parse json or Object");
        }

    }


}
