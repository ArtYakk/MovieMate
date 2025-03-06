package com.artemyakkonen.spring.boot.moviemate.repository;

import com.artemyakkonen.spring.boot.moviemate.entity.Movie;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends CrudRepository<Movie, Long> {
}
