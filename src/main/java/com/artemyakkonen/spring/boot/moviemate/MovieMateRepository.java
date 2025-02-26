package com.artemyakkonen.spring.boot.moviemate;

import com.artemyakkonen.spring.boot.moviemate.entity.Movie;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
interface MovieMateRepository extends CrudRepository<Movie, Long> {
}
