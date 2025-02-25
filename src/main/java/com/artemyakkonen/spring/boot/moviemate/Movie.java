package com.artemyakkonen.spring.boot.moviemate;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;


@Entity
@Table(name = "movies")

@Data
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor

public class Movie{
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @NonNull
        private Long id;

        @Column
        @NonNull
        private String title;

        @Column
        @NonNull
        private String director;

        @Column
        private String genre;

        @Column
        @NonNull
        private Long year;

        @Column
        private String description;

        @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL, orphanRemoval = true, targetEntity = Review.class)
        private List<Review> reviews;
}

