package com.sideagroup.academy.service.impl;

import com.sideagroup.academy.DTO.CelebrityDTO;
import com.sideagroup.academy.DTO.GetAllCelebritiesResponseDTO;
import com.sideagroup.academy.exception.GenericServiceException;
import com.sideagroup.academy.mapper.CelebrityMapper;
import com.sideagroup.academy.mapper.MovieCelebrityMapper;
import com.sideagroup.academy.model.Celebrity;
import com.sideagroup.academy.repository.CelebrityRepository;
import com.sideagroup.academy.service.CelebrityService;
import com.sideagroup.academy.validator.CelebrityValidator;
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
public class CelebrityDbService implements CelebrityService {

    private static final Logger logger = LoggerFactory.getLogger(CelebrityDbService.class);

    @Autowired
    private CelebrityRepository repo;
    @Autowired
    private CelebrityMapper mapper;
    @Autowired
    private MovieCelebrityMapper movieCelebrityMapper;
    @Autowired
    private CelebrityValidator celebrityValidator;

    @Override
    public GetAllCelebritiesResponseDTO getAll(int page, int size, String orderBy, String name) {
        logger.info("getAll called");
        celebrityValidator.validateQueryParams(orderBy);
       Pageable pageable= PageRequest.of(page, size, Sort.by(orderBy));
        Page<Celebrity> celebrities = name == null ? repo.findAll(pageable) :
                repo.findByPrimaryNameIgnoreCaseContaining(name, pageable);
        return mapper.toDto(celebrities, size);
    }



    public Optional<CelebrityDTO> getById(String id) {
        logger.info("getById called");

        Optional<Celebrity> result = repo.findById(id);
        if (!result.isEmpty()) {
            CelebrityDTO dto = mapper.toDto(result.get(), true);
            return Optional.of(dto);
        }
        return Optional.empty();
    }

    public CelebrityDTO create(CelebrityDTO celebrity) {
        logger.info("create called");

        Optional<Celebrity> opt = repo.findById(celebrity.getId());
        if (!opt.isEmpty())
            throw new GenericServiceException("Celebrity with id " + celebrity.getId() + " already exists");
        Celebrity entity = repo.save(mapper.toEntity(celebrity));
        return mapper.toDto(entity, false);
    }

    public Optional<CelebrityDTO> update(String id, CelebrityDTO celebrity) {
        logger.info("update called");

        Optional<Celebrity> opt = repo.findById(id);
        if (opt.isEmpty()) return Optional.empty();
        Celebrity entity = opt.get();
        mapper.updateFromDto(entity, celebrity);
        entity = repo.save(entity);
        return Optional.of(mapper.toDto(entity, false));
    }

    public boolean deleteById(String id) {
        logger.info("deleteById called");

        repo.deleteById(id);
        return true;
    }


}
