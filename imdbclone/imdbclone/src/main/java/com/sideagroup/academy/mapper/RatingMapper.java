package com.sideagroup.academy.mapper;

import com.sideagroup.academy.DTO.RatingDTO;
import com.sideagroup.academy.model.Rating;
import org.springframework.stereotype.Component;

@Component
public class RatingMapper {

    public RatingDTO toDto(Rating rating)
    {
        RatingDTO dto=new RatingDTO();
        dto.setAverageRating(rating.getAverageRating());
        dto.setNumVotes(rating.getNumVotes());
        return dto;


    }
    public Rating toEntity (RatingDTO dto){
        Rating entity= new Rating();
        entity.setAverageRating(dto.getAverageRating());
        entity.setNumVotes(dto.getNumVotes());
        return entity;
    }
}
