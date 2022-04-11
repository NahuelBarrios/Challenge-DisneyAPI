package com.disneyAPI.controller;

import com.disneyAPI.dtos.GenderDTO;
import com.disneyAPI.dtos.GenderDTOCreation;
import com.disneyAPI.dtos.GenderMovieDTO;
import com.disneyAPI.dtos.GenderUpdateDTO;
import com.disneyAPI.exceptions.DisneyRequestException;
import java.util.List;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

public interface GenderController {

    @PostMapping("/genders")
    @ResponseStatus(HttpStatus.CREATED)
    GenderDTO createGender(@RequestBody GenderDTOCreation genderDTOCreation);

    @PutMapping("/genders/{id}")
    @ResponseStatus(HttpStatus.OK)
    GenderDTO updateGender(@PathVariable Integer id, @Valid @RequestBody GenderUpdateDTO genderUpdateDTO) throws DisneyRequestException;

    @GetMapping("/genders")
    @ResponseStatus(HttpStatus.OK)
    List<GenderDTO> getAll();

    @PutMapping("/genders/add/{id}")
    @ResponseStatus(HttpStatus.OK)
    GenderDTO addMovie(@PathVariable Integer id, @RequestBody GenderMovieDTO genderMovieDTO) throws DisneyRequestException;
}
