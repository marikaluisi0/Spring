package com.sideagroup.academy.controller;

import com.sideagroup.academy.DTO.*;
import com.sideagroup.academy.exception.GenericServiceException;
import com.sideagroup.academy.service.MovieService;
import com.sideagroup.academy.service.impl.MovieDbService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/movies") //ioc
@Tag(name = "Movies", description = "Movies management APIs")
@CrossOrigin(origins = "*")
public class MovieController {

    //private MovieDbService movieService= new MovieDbService();
    //private MovieService movieService;
    @Autowired
    private MovieService movieService;

    //@Qualifier("mainMovieService") //quando un'interfaccia viene implementata in più classi e quindi si da un qaulifier.
    //SI COMMENTA PERCHE DO IL QUALIFIER NELLA CLASSE SERVICE PRINCIPALE


    @Operation(summary = "Retrieves all movies without cast",
            description = "Retrieves all movies without cast in paginated way")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Successfully retrieved"),
            @ApiResponse(responseCode = "400", description = "One or more parameters are invalid",
                    content = @Content(schema = @Schema(implementation = DefaultErrorDTO.class))
            )})
    @GetMapping
    public GetAllMoviesResponseDTO getAll(
            @RequestParam(name = "page", required = false, defaultValue = "0")
            @Parameter(description = "Page number", example = "0") int page,
            @RequestParam(name = "size", required = false, defaultValue = "20")
            @Parameter(description = "Page size", example = "30") int size,
            @RequestParam(name = "order_by", required = false, defaultValue = "id")
            @Parameter(description = "Field used for sorting", example = "id") String orderBy,
            @RequestParam(name = "title", required = false)
            @Parameter(description = "Searches for movies with title like this string", example = "star") String title) {
        try {
            return movieService.getAll(page, size, orderBy, title);
        } catch (GenericServiceException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @Operation(summary = "Gets a movie with cast by id", description = "Returns a movie with cast as per the id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(
                    responseCode = "404",
                    description = "Not found- The Movie was not found",
                    content = @Content(schema = @Schema(implementation = DefaultErrorDTO.class))
            )
    })
    @GetMapping("/{id}")
    public MovieDTO getMovie(
            @PathVariable("id")
            @Parameter(description = "Movie id", example = "tt012804") String id) {
        Optional<MovieDTO> opt = movieService.getById(id);
        if (opt.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "item not found");

        }
        return opt.get();
    }


    @Operation(summary = "Updates a movie", description = "Updates the movie with the given id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated"),
            @ApiResponse(
                    responseCode = "404",
                    description = "Not found- The movie was not found",
                    content = @Content(schema = @Schema(implementation = DefaultErrorDTO.class))
            )
    })
    @PutMapping("{id}")
    public MovieDTO update(@PathVariable String id, @RequestBody MovieDTO movie) {

        Optional<MovieDTO> opt = movieService.update(id, movie);
        if (opt.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "item not found");


        return opt.get();}

       /* try {
            return movieService.create(movie);
        } catch (GenericServiceException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }

    }*/

    @Operation(summary = "Deletes a movie", description = "Deletes the movie with the given id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully deleted"),
    })
    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    //idempotente sia la delete che la put, perchè posso chiamarla n volte e il ris non cambia
    //nocontent= non mi restituisce niente
    public void deleteMovie(@PathVariable String id) {
        movieService.deleteById(id);
    }


    @Operation(summary = "Creates a new movie", description = "Creates a new movie")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created"),
            @ApiResponse(
                    responseCode = "400",
                    description = "A movie with same id already exists",
                    content = @Content(schema = @Schema(implementation = DefaultErrorDTO.class))
            )
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MovieDTO create(@RequestBody MovieDTO movie) {
        try {
            return movieService.create(movie);
        } catch (GenericServiceException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }


    @Operation(summary = "Associate a celebrity with a movie", description = "Associate the celebrity 'celebrity id' with the movie 'movieId'")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated"),
            @ApiResponse(
                    responseCode = "404",
                    description = "Not found- The movie was not found",
                    content = @Content(schema = @Schema(implementation = DefaultErrorDTO.class))
            )
    })
    @PutMapping("{movieId}/celebrities/{celebrityId}")
    public MovieCelebrityDTO associateCelebrity(
            @PathVariable String movieId,
            @PathVariable String celebrityId,
            @RequestBody MovieCelebrityDTO body
    ) {
        try {
            return movieService.associateCelebrity(movieId, celebrityId, body);
        } catch (GenericServiceException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @Operation(summary = "Remove the celebrity 'celebrityId' from the 'movieId'", description = "Removes the celebrity 'celebrityIdì from the movie 'movieId")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully deleted"),
    })
    @DeleteMapping("{movieId}/celebrities/{celebrityId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)

    public void deleteAssociation(@PathVariable String movieId,
                                  @PathVariable String celebrityId) {
        movieService.deleteAssociation(movieId, celebrityId);
    }

}




