package com.movieflix.movieApi.repositories;

import com.movieflix.movieApi.entries.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, Integer> {
}
