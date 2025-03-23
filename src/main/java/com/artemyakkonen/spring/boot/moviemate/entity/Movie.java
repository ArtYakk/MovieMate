package com.artemyakkonen.spring.boot.moviemate.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;


@Entity
@Table(name = "movies")

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class Movie{
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column
        private String title;

        @Column
        private String director;

        @Column
        private String genre;

        @Column
        private Long year;

        @Column
        private String description;

        @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL, orphanRemoval = true, targetEntity = Review.class) //FetchType
        private List<Review> reviews;
}

