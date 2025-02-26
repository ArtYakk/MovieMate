package com.artemyakkonen.spring.boot.moviemate.dto;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MovieDTO{
    @NonNull
    private Long id;

    @NonNull
    private String title;

    @NonNull
    private String director;

    private String genre;
    @NonNull
    private Long year;

    private String description;

    private List<ReviewDTO> reviews;
}


