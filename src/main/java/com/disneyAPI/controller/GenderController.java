package com.disneyAPI.controller;

import com.disneyAPI.dtos.GenderDTO;
import com.disneyAPI.dtos.GenderDTOCreation;
import com.disneyAPI.dtos.GenderUpdateDTO;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
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
    GenderDTO createGender(@PathVariable Integer id, @Valid @RequestBody GenderUpdateDTO genderUpdateDTO);
}
