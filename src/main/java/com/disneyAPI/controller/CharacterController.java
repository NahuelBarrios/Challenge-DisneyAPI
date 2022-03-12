package com.disneyAPI.controller;

import com.disneyAPI.domain.Character;
import com.disneyAPI.dtos.CharacterDTO;
import com.disneyAPI.dtos.CharacterDTOCreation;
import com.disneyAPI.dtos.CharacterDTOList;
import com.disneyAPI.dtos.CharacterUpdateDTO;
import com.disneyAPI.dtos.ErrorDTO;
import com.disneyAPI.exceptions.CharacterNotFoundException;
import com.disneyAPI.mapper.CharacterMapper;
import com.disneyAPI.service.CharacterService;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CharacterController {

    private final CharacterService characterService;

    public CharacterController(CharacterService characterService){
        this.characterService = characterService;
    }

    @PostMapping("/characters")
    public ResponseEntity<CharacterDTO> createCharacter(@Valid @RequestBody CharacterDTOCreation characterDTOCreation){
        Character characterDomain = CharacterMapper.mapDTOCreationTODomain(characterDTOCreation);
        CharacterDTO characterDTO = CharacterMapper.mapDomainToDTO(characterService.createCharacter(characterDomain));
        return ResponseEntity.ok(characterDTO);
    }

    @GetMapping("/characters")
    public ResponseEntity<List<CharacterDTOList>> getAll(){
        List<CharacterDTOList> characterDTOList = characterService.getAll()
                .stream().map(CharacterMapper::mapDomainToDTOList)
                .collect(Collectors.toList());
        return ResponseEntity.ok(characterDTOList);
    }

    @GetMapping("/characters/{id}")
    public ResponseEntity<CharacterDTO> getDetailsCharacter(@PathVariable Integer id) throws CharacterNotFoundException {
        return ResponseEntity.ok(CharacterMapper.mapDomainToDTO(characterService.getById(id)));
    }

    @GetMapping(value = "/characters", params = "name")
    public ResponseEntity<List<CharacterDTO>> getNameCharacter(@RequestParam String name) {
        List<CharacterDTO> characterDTOList = characterService.getName(name)
                .stream().map(CharacterMapper::mapDomainToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(characterDTOList);
    }

    @GetMapping(value = "/characters", params = "age")
    public ResponseEntity<List<CharacterDTO>> getAgeCharacter(@RequestParam Integer age) {
        List<CharacterDTO> characterDTOList = characterService.getAge(age)
                .stream().map(CharacterMapper::mapDomainToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(characterDTOList);
    }

    @DeleteMapping("/characters/{id}")
    public ResponseEntity<?> deleteCharacter(@PathVariable Integer id) throws CharacterNotFoundException {
        characterService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ExceptionHandler(CharacterNotFoundException.class)
    public ResponseEntity<ErrorDTO> handleCharacterNotFoundExceptions(CharacterNotFoundException ex) {
        ErrorDTO characterNotFound = ErrorDTO.builder()
                .code(HttpStatus.NOT_FOUND)
                .message(ex.getMessage()).build();
        return new ResponseEntity(characterNotFound, HttpStatus.NOT_FOUND);
    }

}
