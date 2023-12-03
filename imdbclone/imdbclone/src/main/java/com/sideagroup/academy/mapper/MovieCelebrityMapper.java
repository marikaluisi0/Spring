package com.sideagroup.academy.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sideagroup.academy.DTO.MovieCelebrityDTO;
import com.sideagroup.academy.model.MovieCelebrity;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class MovieCelebrityMapper {

    protected String normalizeCharacters(String characters) {
        if (characters == null) return null;
        try {
            ObjectMapper mapper = new ObjectMapper();
            List<String> characterList = Arrays.asList(mapper.readValue(characters, String[].class));
            return characterList.toString().replace("[", "").replace("]", "");
        } catch (JsonProcessingException e) {
            return characters;
        }
    }

    public MovieCelebrityDTO toDto(MovieCelebrity entity) {
        MovieCelebrityDTO dto = new MovieCelebrityDTO();
        dto.setCategory(entity.getCategory());
        dto.setCharacters(normalizeCharacters(entity.getCharacters()));
        dto.setMovieTitle(entity.getMovie().getTitle());
        dto.setCelebrityName(entity.getCelebrity().getPrimaryName());
        dto.setCelebrityId(entity.getId().getCelebrityId());
        dto.setMovieId(entity.getId().getMovieId());
        return dto;
    }
}
