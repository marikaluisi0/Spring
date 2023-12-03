package com.sideagroup.academy.DTO;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
public class GetAllMoviesResponseDTO {
    private PaginationDTO pagination;
    private List<MovieDTO> movies;

    public GetAllMoviesResponseDTO() {
        this.pagination = new PaginationDTO();
        this.movies = new ArrayList<>();
    }



}
