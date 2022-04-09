package com.disneyAPI.controller.impl;

import com.disneyAPI.controller.GenderController;
import com.disneyAPI.domain.Gender;
import com.disneyAPI.dtos.ErrorDTO;
import com.disneyAPI.dtos.GenderDTO;
import com.disneyAPI.dtos.GenderDTOCreation;
import com.disneyAPI.dtos.GenderMovieDTO;
import com.disneyAPI.dtos.GenderUpdateDTO;
import com.disneyAPI.exceptions.GenderNotFoundException;
import com.disneyAPI.mapper.GenderMapper;
import com.disneyAPI.service.GenderService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GenderResource implements GenderController {

    private final GenderService genderService;

    public GenderResource(GenderService genderService){
        this.genderService = genderService;
    }

    @Override
    public GenderDTO createGender(GenderDTOCreation genderDTOCreation) {
        Gender gender = GenderMapper.mapCreationToDomain(genderDTOCreation);
        GenderDTO genderDTO = GenderMapper.mapDomainToDTO(genderService.createGender(gender));
        return genderDTO;
    }

    @Override
    public GenderDTO updateGender(Integer id, GenderUpdateDTO genderUpdateDTO) throws GenderNotFoundException {
        Gender gender = GenderMapper.mapUpdateToDomain(genderUpdateDTO);
        GenderDTO genderDTO = GenderMapper.mapDomainToDTO(genderService.updateGender(id,gender));
        return genderDTO;
    }

    @Override
    public List<GenderDTO> getAll() {
        List<GenderDTO> genderDTOList = genderService.getAll()
                .stream().map(GenderMapper::mapDomainToDTO)
                .collect(Collectors.toList());
        return genderDTOList;
    }

    @Override
    public GenderDTO addMovie(Integer id, GenderMovieDTO genderMovieDTO) throws GenderNotFoundException{
        GenderDTO genderDTO = GenderMapper.mapDomainToDTO(genderService.updateMovie(id,genderMovieDTO.getId()));
        return genderDTO;
    }

    @ExceptionHandler(GenderNotFoundException.class)
    public ResponseEntity<ErrorDTO> handleGenderNotFoundExceptions(GenderNotFoundException ex) {
        ErrorDTO genderNotFound = ErrorDTO.builder()
                .code(HttpStatus.NOT_FOUND)
                .message(ex.getMessage()).build();
        return new ResponseEntity(genderNotFound, HttpStatus.NOT_FOUND);
    }

}
