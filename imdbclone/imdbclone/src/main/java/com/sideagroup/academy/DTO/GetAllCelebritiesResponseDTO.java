package com.sideagroup.academy.DTO;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
public class GetAllCelebritiesResponseDTO {
    private PaginationDTO pagination;
    private List<CelebrityDTO> celebrities;

    public GetAllCelebritiesResponseDTO(){
        pagination= new PaginationDTO();
        celebrities= new ArrayList<>();
    }

    public PaginationDTO getPagination() {
        return pagination;
    }

    public void setPagination(PaginationDTO pagination) {
        this.pagination = pagination;
    }

    public List<CelebrityDTO> getCelebrities() {
        return celebrities;
    }

    public void setCelebrities(List<CelebrityDTO> celebrities) {
        this.celebrities = celebrities;
    }
}
