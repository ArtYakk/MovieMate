package com.artemyakkonen.spring.boot.moviemate.mapper;

import com.artemyakkonen.spring.boot.moviemate.dto.ReviewDTO;
import com.artemyakkonen.spring.boot.moviemate.entity.Review;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ReviewMapper {

    public ReviewDTO toReviewDTO(Review review) {
        return ReviewDTO.builder()
                .review_id(review.getReview_id())
                .review_author(review.getReview_author())
                .rating(review.getRating())
                .content(review.getContent())
                .build();
    }

    public Review toReview(ReviewDTO reviewDTO) {
        return Review.builder()
                .review_id(reviewDTO.getReview_id())
                .review_author(reviewDTO.getReview_author())
                .rating(reviewDTO.getRating())
                .content(reviewDTO.getContent())
                .build();
    }

    public List<ReviewDTO> toReviewDTOList(List<Review> reviewList) {
        return reviewList.stream()
                .map(this::toReviewDTO).toList();
    }

    public List<Review> toReviewList(List<ReviewDTO> reviewDTOList) {
        return reviewDTOList.stream()
                .map(this::toReview).toList();
    }
}
