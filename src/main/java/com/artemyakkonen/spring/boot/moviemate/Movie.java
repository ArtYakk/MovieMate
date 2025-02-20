package com.artemyakkonen.spring.boot.moviemate;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "movies")
public record Movie(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        Long id,

        @Column
        String title,

        @Column
        String director,

        @Column
        String genre,

        @Column
        Long year,

        @Column
        String description,

        @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL, orphanRemoval = true, targetEntity = Review.class)
        List<Review> reviews
){
        public Movie(Long id, String title, String director, Long year){
                this(id, title, director, "", year, "", null);
        }
}

