package com.artemyakkonen.spring.boot.moviemate;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
interface MovieMateRepository extends CrudRepository<Movie, Long> {
}
