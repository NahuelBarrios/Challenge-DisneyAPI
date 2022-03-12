package com.disneyAPI.service;

import com.disneyAPI.exceptions.CharacterNotFoundException;
import com.disneyAPI.mapper.CharacterMapper;
import com.disneyAPI.repository.CharacterRepository;
import com.disneyAPI.repository.model.CharacterModel;
import com.disneyAPI.domain.Character;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.transaction.annotation.Transactional;

public class CharacterService {

    private final CharacterRepository characterRepository;

    public CharacterService(CharacterRepository characterRepository){
        this.characterRepository = characterRepository;
    }

    @Transactional
    public Character createCharacter(Character character){
        CharacterModel characterModel = characterRepository.save(CharacterMapper.mapDomainToModel(character));
        return CharacterMapper.mapModelToDomain(characterModel);
    }

    @Transactional
    public List<Character> getAll() {
        List<CharacterModel> characterModelList = characterRepository.findAll();
        return characterModelList.stream().map(CharacterMapper::mapModelToDomain)
                .collect(Collectors.toList());
    }

    @Transactional
    public Character getById(Integer id) throws CharacterNotFoundException {
        Optional<CharacterModel> modelOptional = characterRepository.findById(id);
        if (!modelOptional.isEmpty()) {
            CharacterModel characterModel = modelOptional.get();
            return CharacterMapper.mapModelToDomain(characterModel);
        } else {
            throw new CharacterNotFoundException(String.format("Character with ID: %s not found", id));
        }
    }

    @Transactional
    public List<Character> getName(String name) {
        List<CharacterModel> characterModelList = characterRepository.findByName(name);
        return characterModelList.stream().map(CharacterMapper::mapModelToDomain)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<Character> getAge(Integer age) {
        List<CharacterModel> characterModelList = characterRepository.findByAge(age);
        return characterModelList.stream().map(CharacterMapper::mapModelToDomain)
                .collect(Collectors.toList());
    }

    @Transactional
    public void delete (Integer id) throws CharacterNotFoundException {
        Optional<CharacterModel> characterModelOptional = characterRepository.findById(id);
        if(characterModelOptional.isEmpty()){
            throw  new CharacterNotFoundException(String.format("News with ID: %s not found", id));
        }else{
            CharacterModel characterModel = characterModelOptional.get();
            characterRepository.delete(characterModel);
        }
    }

}
