package com.artemyakkonen.spring.boot.moviemate.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "reviews")

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Review{
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        Long review_id;

        @Column
        String review_author;

        @Column
        Long rating;

        @Column
        String content;

        @ManyToOne
        @JoinColumn(name = "movie_id")
        Movie movie;
}


