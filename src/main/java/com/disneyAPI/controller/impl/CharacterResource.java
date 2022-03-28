package com.disneyAPI.controller.impl;

import com.disneyAPI.controller.CharacterController;
import com.disneyAPI.domain.Character;
import com.disneyAPI.dtos.CharacterDTO;
import com.disneyAPI.dtos.CharacterDTOCreation;
import com.disneyAPI.dtos.CharacterDTOList;
import com.disneyAPI.dtos.CharacterMovieDTO;
import com.disneyAPI.dtos.CharacterUpdateDTO;
import com.disneyAPI.dtos.ErrorDTO;
import com.disneyAPI.exceptions.CharacterNotFoundException;
import com.disneyAPI.mapper.CharacterMapper;
import com.disneyAPI.service.CharacterService;
import io.swagger.annotations.Api;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "CharacterResource", tags = {"Characters"})
@RestController
public class CharacterResource implements CharacterController {

    private final CharacterService characterService;

    public CharacterResource(CharacterService characterService){
        this.characterService = characterService;
    }

    @Override
    public CharacterDTO createCharacter( CharacterDTOCreation characterDTOCreation){
        Character characterDomain = CharacterMapper.mapDTOCreationTODomain(characterDTOCreation);
        CharacterDTO characterDTO = CharacterMapper.mapDomainToDTO(characterService.createCharacter(characterDomain));
        return characterDTO;
    }

    @Override
    public CharacterDTO updateCharacter(Integer id, CharacterUpdateDTO characterUpdateDTO) throws CharacterNotFoundException {
        Character character = CharacterMapper.mapUpdateDtoToDomain(characterUpdateDTO);
        CharacterDTO characterDTO = CharacterMapper.mapDomainToDTO(characterService.updateCharacter(id,character));
        return characterDTO;
    }

    @Override
    public CharacterDTO addMovie(Integer id, CharacterMovieDTO characterMovieDTO) throws CharacterNotFoundException{
        CharacterDTO characterDTO = CharacterMapper.mapDomainToDTO(characterService.updateMovie(id,characterMovieDTO.getId()));
        return characterDTO;
    }

    @Override
    public List<CharacterDTOList> getAll(){
        List<CharacterDTOList> characterDTOList = characterService.getAll()
                .stream().map(CharacterMapper::mapDomainToDTOList)
                .collect(Collectors.toList());
        return characterDTOList;
    }

    @Override
    public CharacterDTO getDetailsCharacter(Integer id) throws CharacterNotFoundException {
        return CharacterMapper.mapDomainToDTO(characterService.getById(id));
    }

    @Override
    public List<CharacterDTO> getNameCharacter(String name) {
        List<CharacterDTO> characterDTOList = characterService.getName(name)
                .stream().map(CharacterMapper::mapDomainToDTO)
                .collect(Collectors.toList());
        return characterDTOList;
    }

    @Override
    public List<CharacterDTO> getAgeCharacter(Integer age) {
        List<CharacterDTO> characterDTOList = characterService.getAge(age)
                .stream().map(CharacterMapper::mapDomainToDTO)
                .collect(Collectors.toList());
        return characterDTOList;
    }

    @Override
    public void deleteCharacter(Integer id) throws CharacterNotFoundException {
        characterService.delete(id);
    }

    @ExceptionHandler(CharacterNotFoundException.class)
    public ResponseEntity<ErrorDTO> handleCharacterNotFoundExceptions(CharacterNotFoundException ex) {
        ErrorDTO characterNotFound = ErrorDTO.builder()
                .code(HttpStatus.NOT_FOUND)
                .message(ex.getMessage()).build();
        return new ResponseEntity(characterNotFound, HttpStatus.NOT_FOUND);
    }
}
