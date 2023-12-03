package com.sideagroup.academy.service;

import com.sideagroup.academy.DTO.CelebrityDTO;
import com.sideagroup.academy.DTO.GetAllCelebritiesResponseDTO;

import java.util.Optional;

public interface CelebrityService {

    public GetAllCelebritiesResponseDTO getAll(int page, int size, String orderBy, String name);
    public Optional<CelebrityDTO> getById(String id);
    public CelebrityDTO create(CelebrityDTO celebrity);
    public Optional<CelebrityDTO> update(String id, CelebrityDTO celebrity);
    public boolean deleteById(String id);


}
