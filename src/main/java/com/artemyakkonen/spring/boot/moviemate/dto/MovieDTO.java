package com.artemyakkonen.spring.boot.moviemate.dto;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MovieDTO{
    private Long id;

    private String title;

    private String director;

    private String genre;

    private Long year;

    private String description;

    private String addedBy;

    private List<ReviewDTO> reviews;
}


