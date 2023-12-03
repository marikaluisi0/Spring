package com.sideagroup.academy.controller.api;

import com.sideagroup.academy.DTO.CelebrityDTO;
import com.sideagroup.academy.DTO.DefaultErrorDTO;
import com.sideagroup.academy.DTO.GetAllCelebritiesResponseDTO;
import com.sideagroup.academy.exception.GenericServiceException;
import com.sideagroup.academy.service.CelebrityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/celebrities")
@Tag(name="Celebrities", description= "Celebrities management APIs")
@CrossOrigin(origins = "*")
public class CelebrityController {
    @Autowired
    private CelebrityService celebrityService;

    @Operation(summary = "Retrieves all celebrities without movies",
            description = "Retrieves all celebrities without movies in paginated way")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Successfully retrieved"),
            @ApiResponse(responseCode = "400", description = "One or more parameters are invalid",
                    content = @Content(schema = @Schema(implementation = DefaultErrorDTO.class))
            )})
    @GetMapping
    public GetAllCelebritiesResponseDTO getAll(
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,

            @RequestParam(name = "size", required = false, defaultValue = "20") int size,

            @RequestParam(name = "order_by", required = false, defaultValue = "id") String orderBy,
            @RequestParam(name = "name", required = false) String name) {
        try {
            return celebrityService.getAll(page, size, orderBy, name);
        } catch (GenericServiceException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @Operation(summary = "Gets a celebrity with movie by id", description = "Returns a celebrity with movie as per the id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(
                    responseCode = "404",
                    description = "Not found- The celebrity was not found",
                    content = @Content(schema = @Schema(implementation = DefaultErrorDTO.class))
            )
    })
    @GetMapping("/{id}")
    public CelebrityDTO getCelebrity(
            @PathVariable("id")
            @Parameter(description = "Celebrity id", example="ttt2516") String id) {
        Optional<CelebrityDTO> opt = celebrityService.getById(id);
        if (opt.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "item not found");

        }
        return opt.get();
    }

    @Operation(summary = "Updates a celebrity", description = "Updates the celebrity with the given id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(
                    responseCode = "404",
                    description = "Not found- The celebrity was not found",
                    content = @Content(schema = @Schema(implementation = DefaultErrorDTO.class))
            )
    })
    @PutMapping("{id}")
    public CelebrityDTO update(@PathVariable String id, @RequestBody CelebrityDTO celebrity) {
        Optional<CelebrityDTO> opt = celebrityService.update(id, celebrity);
        if (opt.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "item not found");


        return opt.get();
    }

    @Operation(summary = "Deletes a celebrity", description = "Deletes the celebrity with the given id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully deleted"),
    })
    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMovie(@PathVariable String id) {

        celebrityService.deleteById(id);
    }

    @Operation(summary = "Creates a new celebrity", description = "Creates a new celebrity")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created"),
            @ApiResponse(
                    responseCode = "400",
                    description = "A celebrity with same id already exists",
                    content = @Content(schema = @Schema(implementation = DefaultErrorDTO.class))
            )
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CelebrityDTO create(@RequestBody CelebrityDTO celebrity) {
        try {
            return celebrityService.create(celebrity);
        } catch (GenericServiceException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }


    }
}
