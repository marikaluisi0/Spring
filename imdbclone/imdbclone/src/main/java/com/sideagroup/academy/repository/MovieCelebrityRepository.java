package com.sideagroup.academy.repository;

import com.sideagroup.academy.model.MovieCelebrity;
import com.sideagroup.academy.model.MovieCelebrityKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieCelebrityRepository extends JpaRepository<MovieCelebrity, MovieCelebrityKey> {
}
