package com.disneyAPI.controller;

import com.disneyAPI.domain.Character;
import com.disneyAPI.dtos.CharacterDTO;
import com.disneyAPI.dtos.CharacterDTOCreation;
import com.disneyAPI.dtos.ErrorDTO;
import com.disneyAPI.exceptions.CharacterNotFoundException;
import com.disneyAPI.mapper.CharacterMapper;
import com.disneyAPI.repository.model.CharacterModel;
import com.disneyAPI.service.CharacterService;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CharacterController {

    private final CharacterService characterService;

    public CharacterController(CharacterService characterService){
        this.characterService = characterService;
    }

    @PostMapping("/character")
    public ResponseEntity<CharacterDTO> createCharacter(@Valid @RequestBody CharacterDTOCreation characterDTOCreation){
        Character characterDomain = CharacterMapper.mapDTOCreationTODomain(characterDTOCreation);
        CharacterDTO characterDTO = CharacterMapper.mapDomainToDTO(characterService.createCharacter(characterDomain));
        return ResponseEntity.ok(characterDTO);
    }

    @ExceptionHandler(CharacterNotFoundException.class)
    public ResponseEntity<ErrorDTO> handleCategoryNotFoundExceptions(CharacterNotFoundException ex) {
        ErrorDTO categoryNotFound = ErrorDTO.builder()
                .code(HttpStatus.NOT_FOUND)
                .message(ex.getMessage()).build();
        return new ResponseEntity(categoryNotFound, HttpStatus.NOT_FOUND);
    }

}
