package com.artemyakkonen.spring.boot.moviemate.repository;

import com.artemyakkonen.spring.boot.moviemate.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByName(String username);
}
