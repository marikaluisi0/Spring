package com.sideagroup.academy.mapper;

import com.sideagroup.academy.DTO.CelebrityDTO;
import com.sideagroup.academy.DTO.GetAllCelebritiesResponseDTO;
import com.sideagroup.academy.DTO.GetAllMoviesResponseDTO;
import com.sideagroup.academy.DTO.MovieDTO;
import com.sideagroup.academy.model.Celebrity;
import com.sideagroup.academy.model.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public class CelebrityMapper {

    @Autowired
    private MovieCelebrityMapper movieCelebrityMapper;

    public CelebrityDTO toDto(Celebrity entity, boolean withMovie){
        CelebrityDTO dto= new CelebrityDTO();
        dto.setId(entity.getId());
        dto.setBirthYear(entity.getBirthYear());
        dto.setDeathYear(entity.getDeathYear());
        dto.setName(entity.getPrimaryName());
        if (!withMovie)
            return dto;
        dto.getMovies().addAll(entity.getTitles().stream().map(item->movieCelebrityMapper.toDto(item)).toList());
        return dto;
    }

    public GetAllCelebritiesResponseDTO toDto(Page<Celebrity> celebrities, int size) {
        GetAllCelebritiesResponseDTO dto = new GetAllCelebritiesResponseDTO();
        dto.getPagination().setCurrentPage(celebrities.getNumber());
        dto.getPagination().setTotalElements(celebrities.getTotalElements());
        dto.getPagination().setTotalPages(celebrities.getTotalPages());
        dto.getPagination().setPageSize(size);
        dto.getCelebrities().addAll(celebrities.getContent().stream().map(item -> toDto(item, false)).toList());
        return dto;
    }

        public Celebrity toEntity(CelebrityDTO dto) {
        Celebrity entity = new Celebrity();
        entity.setBirthYear(dto.getBirthYear());
        entity.setId(dto.getId());
        entity.setDeathYear(dto.getDeathYear());
        entity.setPrimaryName(dto.getName());

        return entity;
    }

    public void updateFromDto(Celebrity entity, CelebrityDTO dto) {
        entity.setPrimaryName(dto.getName());
        entity.setDeathYear(dto.getDeathYear());
        entity.setBirthYear(dto.getBirthYear());

    }


}
