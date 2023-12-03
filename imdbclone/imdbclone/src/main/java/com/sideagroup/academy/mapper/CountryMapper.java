package com.sideagroup.academy.mapper;

import com.sideagroup.academy.DTO.CountryDTO;
import com.sideagroup.academy.DTO.RatingDTO;
import com.sideagroup.academy.model.Country;
import org.springframework.stereotype.Component;

@Component
public class CountryMapper {
    public CountryDTO toDto(Country entity)
    {
        CountryDTO dto=new CountryDTO();
        dto.setLanguage(entity.getLanguage());
        dto.setTitle(entity.getTitle());
        dto.setRegion(entity.getRegion());
        return dto;
    }
}
