package com.sideagroup.academy.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
public class DefaultErrorDTO {

    private Date timestamp;
    @Schema(description = "HTTP Status code", example = "404")
    private int status;
    @Schema(description = "HTTP Status message", example = "Not Found")
    private String error;
    @Schema(description = "Error detail", example = "item not found")
    private String message;
    @Schema(description = "Requested path", example = "/api/v1/movies/tt01207378")
    private String path;
}
