package com.sideagroup.academy.service.impl;
import com.sideagroup.academy.DTO.GetAllMoviesResponseDTO;
import com.sideagroup.academy.DTO.MovieCelebrityDTO;
import com.sideagroup.academy.DTO.MovieDTO;
import com.sideagroup.academy.exception.GenericServiceException;
import com.sideagroup.academy.mapper.MovieCelebrityMapper;
import com.sideagroup.academy.mapper.MovieMapper;
import com.sideagroup.academy.mapper.RatingMapper;
import com.sideagroup.academy.model.*;
import com.sideagroup.academy.repository.CelebrityRepository;
import com.sideagroup.academy.repository.MovieCelebrityRepository;
import com.sideagroup.academy.repository.MovieRepository;
import com.sideagroup.academy.repository.RatingRepository;
import com.sideagroup.academy.service.CelebrityService;
import com.sideagroup.academy.service.MovieService;
import com.sideagroup.academy.validator.MovieValidator;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MovieDbService implements MovieService {
    private static final Logger logger = LoggerFactory.getLogger(MovieDbService.class);
    @Autowired
    private MovieRepository repo;
    @Autowired
    private MovieMapper mapper;
    @Autowired
    private MovieCelebrityMapper movieCelebrityMapper;
    @Autowired
    private MovieCelebrityRepository movieCelebrityRepository;

    @Autowired
    private CelebrityRepository celebrityRepo;
    @Autowired
    private RatingMapper ratingMapper;
    @Autowired
    private RatingRepository ratingRepository;
    @Autowired
    private MovieValidator movieValidator;


    public GetAllMoviesResponseDTO getAll(int page, int size, String orderBy ,String title) {
        logger.info("getAll called");
        movieValidator.validateQueryParams(orderBy);
        Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));
        Page<Movie> movies = title == null ? repo.findAll(pageable) :
                repo.findByTitle("%" + title + "%", pageable);
        return mapper.toDto(movies, size);
    }



    public Optional<MovieDTO> getById(String id) {
        logger.info("getById called");

        Optional<Movie> result = repo.findById(id);
        if (!result.isEmpty()) {
            MovieDTO dto = mapper.toDto(result.get(), true, true);
            return Optional.of(dto);
        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public MovieDTO create(MovieDTO movie) {
        logger.info("create called");
        movieValidator.validateCreateMovieRequest(movie);
        Optional<Movie> opt=repo.findById(movie.getId());
        if (!opt.isEmpty())
            throw new GenericServiceException("Movie with id " + movie.getId() + " already exists");
        Movie movieEntity = mapper.toEntity(movie);
        movieEntity = repo.save(movieEntity);
        Rating ratingEntity = ratingMapper.toEntity(movie.getRating());
        ratingEntity.setMovie(movieEntity);
        ratingEntity = ratingRepository.save(ratingEntity);
        movieEntity.setRating(ratingEntity);
        return mapper.toDto(movieEntity, false, false);
    }

    public Optional<MovieDTO> update(String id, MovieDTO movie) {
        logger.info("update called");

        Optional<Movie> opt = repo.findById(id);
        if (opt.isEmpty()) return Optional.empty();
        Movie entity = opt.get();
        mapper.updateFromDto(entity, movie);
        entity = repo.save(entity);
        return Optional.of(mapper.toDto(entity, false, false));
    }

    @Override
    @Transactional
    public boolean deleteById(String id) {
        logger.info("deleteById called");
        Optional<Movie> movie = repo.findById(id);
        if (movie.isEmpty())
            return false;
        ratingRepository.deleteById((movie.get().getRating().getId()));
        repo.deleteById(id);
        return true;
    }

    @Override
    public MovieCelebrityDTO associateCelebrity(String movieId, String celebrityId, MovieCelebrityDTO body) {
        Optional<Movie> movie = repo.findById(movieId);
        if (movie.isEmpty())
            throw new GenericServiceException("Movie with id " + movieId + " does not exists");
        Optional<Celebrity> celebrity = celebrityRepo.findById(celebrityId);
        if (celebrity.isEmpty())
            throw new GenericServiceException("Celebrity with id " + celebrityId + " does not exists");
        MovieCelebrityKey key = new MovieCelebrityKey(celebrityId, movieId);
        Optional<MovieCelebrity> rel = movieCelebrityRepository.findById(key);

        if (!rel.isEmpty()) {
            return movieCelebrityMapper.toDto(rel.get());
        }
        MovieCelebrity entity = new MovieCelebrity(key);
        entity.setCelebrity(celebrity.get());
        entity.setMovie(movie.get());
        entity.setCategory(body.getCategory());
        entity.setCharacters(body.getCharacters());
        entity = movieCelebrityRepository.save(entity);
        return movieCelebrityMapper.toDto(entity);

    }

    public boolean deleteAssociation(String celebrityId, String movieId) {
   //non c'è bisgono di fare i controlli con la delete. SOLO PASSARE CHIAVE

        MovieCelebrityKey key = new MovieCelebrityKey(celebrityId, movieId);
        movieCelebrityRepository.deleteById(key);
        return true;
    }




}




