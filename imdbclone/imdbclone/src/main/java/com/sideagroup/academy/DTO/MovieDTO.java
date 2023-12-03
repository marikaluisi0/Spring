package com.sideagroup.academy.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
public class MovieDTO {
    @Schema(description = "Movie Id", example = "404", requiredMode = Schema.RequiredMode.REQUIRED)
    private String id;
    @Schema(description = "Movie title", requiredMode = Schema.RequiredMode.REQUIRED)
    private String title;
    @Schema(description = "Movie year", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer year;
    @Schema(description = "Running time in minutes", example = "178", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer runningTime;
    @Schema(description = "Comma separated genres of a movie", example = "Action, Adventure", requiredMode = Schema.RequiredMode.REQUIRED)
    private String genres;
    @Schema(description = "Movie cast", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private List<MovieCelebrityDTO> cast;
    @Schema(description = "Movie rating", requiredMode = Schema.RequiredMode.REQUIRED)
    private RatingDTO rating;
    @Schema(description = "Movie countries", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private List<CountryDTO> countries;


    public MovieDTO() {
    cast= new ArrayList<>();
    countries= new ArrayList<>();
    }

}
