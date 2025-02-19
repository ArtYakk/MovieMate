package com.artemyakkonen.spring.boot.moviemate;

import java.util.Objects;

public class Review {

    private int review_id;
    private int movie_id;
    private String review_author;
    private int rating;
    private String content;

    public Review() {
    }

    public Review(int review_id, int movie_id, String review_author, int rating, String content) {
        this.review_id = review_id;
        this.movie_id = movie_id;
        this.review_author = review_author;
        this.rating = rating;
        this.content = content;
    }

    public int getReview_id() {
        return review_id;
    }

    public void setReview_id(int review_id) {
        this.review_id = review_id;
    }

    public int getMovie_id() {
        return movie_id;
    }

    public void setMovie_id(int movie_id) {
        this.movie_id = movie_id;
    }

    public String getReview_author() {
        return review_author;
    }

    public void setReview_author(String review_author) {
        this.review_author = review_author;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Review{" +
                "review_id=" + review_id +
                ", movie_id=" + movie_id +
                ", review_author='" + review_author + '\'' +
                ", rating=" + rating +
                ", content='" + content + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Review review = (Review) o;
        return movie_id == review.movie_id && rating == review.rating && Objects.equals(review_author, review.review_author) && Objects.equals(content, review.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(movie_id, review_author, rating, content);
    }
}
