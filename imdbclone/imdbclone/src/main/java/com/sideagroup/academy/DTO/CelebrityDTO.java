package com.sideagroup.academy.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class CelebrityDTO {
    @Schema(description = "Celebrity Id", example = "tt1023", requiredMode = Schema.RequiredMode.REQUIRED)
    private String id;
    @Schema(description = "Celebrity name", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;
    @Schema(description = "Celebrity birthYear", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer birthYear;
    @Schema(description = "Celebrity death year", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer deathYear;
    @Schema(description = "Movie List", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private  List<MovieCelebrityDTO> movies;

   public CelebrityDTO(){
       movies= new ArrayList<>();
   }


}
