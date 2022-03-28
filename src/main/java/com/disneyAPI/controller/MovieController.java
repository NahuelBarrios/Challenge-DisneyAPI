package com.disneyAPI.controller;

import com.disneyAPI.dtos.MovieDTO;
import com.disneyAPI.dtos.MovieDTOCreation;
import com.disneyAPI.dtos.MovieDTOList;
import com.disneyAPI.exceptions.MovieNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

@Tag(name = "Movies", description = "Operations related to Movies")
public interface MovieController {

    @Operation(
            summary = "Create new movie",
            description = "To create a movie, you must access this endpoint.")
    @PostMapping("/movies")
    @ResponseStatus(HttpStatus.CREATED)
     MovieDTO createMovie(@Valid @RequestBody MovieDTOCreation movieDTOCreation);

    @Operation(
            summary = "Get movies list",
            description = "To get a list of the movies")
    @GetMapping("/movies")
    @ResponseStatus(HttpStatus.OK)
    List<MovieDTOList> getAll();

    @Operation(
            summary = "Get a movie by Id",
            description = "To get a movie by its Id you must access this endpoint"

    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get a movie by Id",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = MovieDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid Id supplied", content = @Content),
            @ApiResponse(responseCode = "404", description = "Movie not found", content = @Content)
    })
    @GetMapping("/movies/{id}")
    @ResponseStatus(HttpStatus.OK)
    MovieDTO getDetailsMovie(@PathVariable Integer id) throws MovieNotFoundException;

    @Operation(
            summary = "Get movies list by Movies Name",
            description = "To get a list of the movies, filtering by movies name, you must access this param.")
    @GetMapping(value = "/movies", params = "name")
    @ResponseStatus(HttpStatus.OK)
    List<MovieDTO> getNameMovie(@RequestParam String name);

    @Operation(
            summary = "Get movies list by Movies order",
            description = "To get a list of the movies, filtering by movies order, you must access this param.")
    @GetMapping(value = "/movies", params = "order")
    @ResponseStatus(HttpStatus.OK)
    List<MovieDTO> getMoviesOrder(@RequestParam(defaultValue = "ASC") String order) throws MovieNotFoundException;
}
