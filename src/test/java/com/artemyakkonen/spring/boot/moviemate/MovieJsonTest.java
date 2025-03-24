package com.artemyakkonen.spring.boot.moviemate;

import com.artemyakkonen.spring.boot.moviemate.entity.Movie;
import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
public class MovieJsonTest {

    private final JacksonTester<Movie> json;
    private final JacksonTester<Movie[]> jsonList;
    private Movie[] movies;

    @Autowired
    public MovieJsonTest(JacksonTester<Movie> json, JacksonTester<Movie[]> jsonList){
        this.json = json;
        this.jsonList = jsonList;
    }

    @BeforeEach
    void setUp(){
        movies = Arrays.array(
                Movie.builder()
                        .id(1L)
                        .title("Inception")
                        .genre("Sci-Fi")
                        .director("Christopher Nolan")
                        .year(2010L)
                        .addedBy("admin")
                        .build(),
                Movie.builder()
                        .id(2L)
                        .title("The Godfather")
                        .genre("Crime")
                        .director("Francis Ford Coppola")
                        .year(1972L)
                        .addedBy("admin")
                        .build(),
                Movie.builder()
                        .id(3L)
                        .title("The Dark Knight")
                        .genre("Action")
                        .director("Christopher Nolan")
                        .year(2008L)
                        .addedBy("admin")
                        .build()
        );
    }

    @Test
    void MovieSerializationTest() throws IOException {
        Movie movie = Movie.builder()
                .id(11L)
                .title("War and Peace")
                .director("Lev Tolstoy")
                .year(1867L)
                .addedBy("admin")
                .build();

        assertThat(json.write(movie)).isStrictlyEqualToJson("single.json");

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
                  "reviews": null,
                  "addedBy": "admin"
                }
                """;
        try {
            assertThat(json.parse(expected)).isEqualTo(Movie.builder()
                    .id(11L)
                    .title("War and Peace")
                    .director("Lev Tolstoy")
                    .year(1867L)
                    .addedBy("admin")
                    .build());
            assertThat(json.parseObject(expected).getId()).isEqualTo(11L);
            assertThat(json.parseObject(expected).getTitle()).isEqualTo("War and Peace");
            assertThat(json.parseObject(expected).getDirector()).isEqualTo("Lev Tolstoy");
            assertThat(json.parseObject(expected).getYear()).isEqualTo(1867L);
        } catch (IOException e) {
            System.out.println("Failed to parse json or Object");
        }

    }

    @Test
    void movieListSerializationTest() throws IOException {
        assertThat(jsonList.write(movies)).isStrictlyEqualToJson("list.json");
    }

    @Test
    void movieListDeserializationTest() throws IOException {
        String testJson = """
                [
                  {
                    "id": 1,
                    "title": "Inception",
                    "director": "Christopher Nolan",
                    "genre": "Sci-Fi",
                    "year": 2010,
                    "description": null,
                    "reviews": null,
                    "addedBy": "admin"
                  },
                  {
                    "id": 2,
                    "title": "The Godfather",
                    "director": "Francis Ford Coppola",
                    "genre": "Crime",
                    "year": 1972,
                    "description": null,
                    "reviews": null,
                    "addedBy": "admin"
                  },
                  {
                    "id": 3,
                    "title": "The Dark Knight",
                    "director": "Christopher Nolan",
                    "genre": "Action",
                    "year": 2008,
                    "description": null,
                    "reviews": null,
                    "addedBy": "admin"
                  }
                ]
                """;

        assertThat(jsonList.parse(testJson)).isEqualTo(movies);
    }


}
