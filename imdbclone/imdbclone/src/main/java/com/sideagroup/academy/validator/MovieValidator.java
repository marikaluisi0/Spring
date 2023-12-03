package com.sideagroup.academy.validator;

import com.sideagroup.academy.DTO.MovieDTO;
import com.sideagroup.academy.exception.GenericServiceException;
import org.springframework.stereotype.Component;

@Component
public class MovieValidator {

    public void validateQueryParams(String orderBy) {
        if (!"id".equals(orderBy) && !"title".equals(orderBy) && !"year".equals(orderBy)) {
            throw new GenericServiceException("Invalid Sort field '" + orderBy + "'. Valid values are: [id, title, year]");
        }

    }

    public void validateCreateMovieRequest(MovieDTO movie) {
        if (movie.getRating() == null)
            throw new GenericServiceException("Missing field 'rating'");
    }

}
