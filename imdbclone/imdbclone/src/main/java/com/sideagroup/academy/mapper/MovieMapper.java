package com.sideagroup.academy.mapper;

import com.sideagroup.academy.DTO.GetAllMoviesResponseDTO;
import com.sideagroup.academy.DTO.MovieDTO;
import com.sideagroup.academy.model.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public class MovieMapper {
    @Autowired
    private MovieCelebrityMapper movieCelebrityMapper;
    @Autowired
    private CountryMapper countryMapper;
    @Autowired
    private RatingMapper ratingMapper;


    public MovieDTO toDto(Movie entity, boolean withCast, boolean withCountry) {
        MovieDTO dto = new MovieDTO();
        dto.setTitle(entity.getTitle());
        dto.setId(entity.getId());
        dto.setYear(entity.getYear());
        dto.setRunningTime(entity.getRuntimeMinutes());
        dto.setGenres(entity.getGenres());
        dto.setRating(ratingMapper.toDto(entity.getRating()));
        if (withCast)
             dto.getCast().addAll(entity.getNames().stream().map(item->movieCelebrityMapper.toDto(item)).toList());
        if(withCountry)
        dto.getCountries().addAll(entity.getCountries().stream().map(item -> countryMapper.toDto(item)).toList());
        return dto;}

    public GetAllMoviesResponseDTO toDto(Page<Movie> movies, int size) {
        GetAllMoviesResponseDTO dto = new GetAllMoviesResponseDTO();
        dto.getPagination().setCurrentPage(movies.getNumber());
        dto.getPagination().setTotalElements(movies.getTotalElements());
        dto.getPagination().setTotalPages(movies.getTotalPages());
        dto.getPagination().setPageSize(size);
        dto.getMovies().addAll(movies.getContent().stream().map(item -> toDto(item, true, false)).toList());
        return dto;
    }

    public Movie toEntity(MovieDTO dto) {
        Movie entity = new Movie();
        entity.setTitle(dto.getTitle());
        entity.setGenres(dto.getGenres());
        entity.setId(dto.getId());
        entity.setYear(dto.getYear());
        entity.setRuntimeMinutes(dto.getRunningTime());

        return entity;
    }

    public void updateFromDto(Movie entity, MovieDTO dto) {
        entity.setTitle(dto.getTitle());
        entity.setGenres(dto.getGenres());
        entity.setYear(dto.getYear());
        entity.setRuntimeMinutes(dto.getRunningTime());
    }


}
