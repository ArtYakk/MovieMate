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
        Movie movie = new Movie(11, "War and Peace", "Lev Tolstoy", 1867);
        assertThat(json.write(movie)).isStrictlyEqualToJson("expected.json");

        assertThat(json.write(movie)).hasJsonPathNumberValue("@.id");
        assertThat(json.write(movie)).extractingJsonPathNumberValue("@.id").isEqualTo(11);

        assertThat(json.write(movie)).hasJsonPathStringValue("@.title");
        assertThat(json.write(movie)).extractingJsonPathStringValue("@.title").isEqualTo("War and Peace");

        assertThat(json.write(movie)).hasJsonPathStringValue("@.author");
        assertThat(json.write(movie)).extractingJsonPathStringValue("@.author").isEqualTo("Lev Tolstoy");

        assertThat(json.write(movie)).hasJsonPathNumberValue("@.year");
        assertThat(json.write(movie)).extractingJsonPathNumberValue("@.year").isEqualTo(1867);
    }

    @Test
    void MovieDeserializationTest(){
        String expected = """
                {
                  "id": 11,
                  "title": "War and Peace",
                  "author": "Lev Tolstoy",
                  "genre": null,
                  "year": 1867,
                  "description": null,
                  "reviews": null
                }
                """;
        try {
            assertThat(json.parse(expected)).isEqualTo(new Movie(11, "War and Peace", "Lev Tolstoy", 1867));
            assertThat(json.parseObject(expected).getId()).isEqualTo(11);
            assertThat(json.parseObject(expected).getTitle()).isEqualTo("War and Peace");
            assertThat(json.parseObject(expected).getAuthor()).isEqualTo("Lev Tolstoy");
            assertThat(json.parseObject(expected).getYear()).isEqualTo(1867);
        } catch (IOException e) {
            System.out.println("Failed to parse json or Object");
        }

    }


}
