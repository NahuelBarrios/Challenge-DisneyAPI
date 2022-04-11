package com.disneyAPI.controller;

import com.disneyAPI.dtos.CharacterDTO;
import com.disneyAPI.dtos.CharacterDTOCreation;
import com.disneyAPI.dtos.CharacterDTOList;
import com.disneyAPI.dtos.CharacterMovieDTO;
import com.disneyAPI.dtos.CharacterUpdateDTO;
import com.disneyAPI.exceptions.DisneyRequestException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

@Tag(name = "Characters", description = "Operations related to Characters")
public interface CharacterController {

    @Operation(
            summary = "Create new character",
            description = "To create a character, you must access this endpoint.")
    @PostMapping("/characters")
    @ResponseStatus(HttpStatus.CREATED)
    CharacterDTO createCharacter(@Valid @RequestBody CharacterDTOCreation characterDTOCreation);


    @Operation(summary = "Update a character by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Update a character by id",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = CharacterDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied", content = @Content),
            @ApiResponse(responseCode = "404", description = "Character not found", content = @Content)})
    @PutMapping("/characters/{id}")
    @ResponseStatus(HttpStatus.OK)
    CharacterDTO updateCharacter(@PathVariable Integer id,
                                                        @RequestBody CharacterUpdateDTO characterUpdateDTO) throws DisneyRequestException;

    @Operation(summary = "add a movie in character by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Update a movie in character by id",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = CharacterDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied", content = @Content),
            @ApiResponse(responseCode = "404", description = "Movie not found", content = @Content)})
    @PutMapping("/characters/add/{id}")
    @ResponseStatus(HttpStatus.OK)
    CharacterDTO addMovie(@PathVariable Integer id, @RequestBody CharacterMovieDTO characterMovieDTO) throws DisneyRequestException;

    @Operation(
            summary = "Get character list",
            description = "To get  list of the characters.")
    @GetMapping("/characters")
    @ResponseStatus(HttpStatus.OK)
    List<CharacterDTOList> getAll();

    @Operation(
            summary = "Get a character by Id",
            description = "To get a character by its Id you must access this endpoint"

    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get a character by Id",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = CharacterDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid Id supplied", content = @Content),
            @ApiResponse(responseCode = "404", description = "Character not found", content = @Content)
    })
    @GetMapping("/characters/{id}")
    @ResponseStatus(HttpStatus.OK)
    CharacterDTO getDetailsCharacter(@PathVariable Integer id) throws DisneyRequestException;

    @Operation(
            summary = "Get character list by Characters Name",
            description = "To get a list of the characters, filtering by characters name, you must access this param.")
    @GetMapping(value = "/characters", params = "name")
    @ResponseStatus(HttpStatus.OK)
    List<CharacterDTO> getNameCharacter(@RequestParam String name);

    @Operation(
            summary = "Get character list by Characters Age",
            description = "To get a list of the characters, filtering by characters age, you must access this param.")
    @GetMapping(value = "/characters", params = "age")
    @ResponseStatus(HttpStatus.OK)
    List<CharacterDTO> getAgeCharacter(@RequestParam Integer age);

    @Operation(summary = "Delete a Character by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Delete a character by id"),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied", content = @Content),
            @ApiResponse(responseCode = "404", description = "Character not found", content = @Content)})
    @DeleteMapping("/characters/{id}")
    @ResponseStatus(HttpStatus.OK)
    void deleteCharacter(@PathVariable Integer id) throws DisneyRequestException;

}
