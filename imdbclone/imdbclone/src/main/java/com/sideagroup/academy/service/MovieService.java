package com.sideagroup.academy.service;

import com.sideagroup.academy.DTO.GetAllMoviesResponseDTO;
import com.sideagroup.academy.DTO.MovieCelebrityDTO;
import com.sideagroup.academy.DTO.MovieDTO;

import java.util.Optional;

public interface MovieService {

    public GetAllMoviesResponseDTO getAll(int page, int size, String orderBy, String title);

    public Optional<MovieDTO> getById(String id);

    public MovieDTO create(MovieDTO movie);

    public Optional<MovieDTO> update(String id, MovieDTO movie);

    public boolean deleteById(String id);

    public MovieCelebrityDTO associateCelebrity(String movieId, String celebrityId, MovieCelebrityDTO body);

    public boolean deleteAssociation(String celebrityId, String movieId);
}
