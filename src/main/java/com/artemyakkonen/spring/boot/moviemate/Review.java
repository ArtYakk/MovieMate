package com.artemyakkonen.spring.boot.moviemate;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "reviews")
public record Review(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        Long review_id,

        @ManyToOne
        @JoinColumn(name = "movie_id")
        Movie movie,

        @Column
        String review_author,

        @Column
        Long rating,

        @Column
        String content
){}


