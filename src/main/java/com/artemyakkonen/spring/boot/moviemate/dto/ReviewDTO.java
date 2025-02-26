package com.artemyakkonen.spring.boot.moviemate.dto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDTO{
    Long review_id;
    String review_author;
    Long rating;
    String content;
}


