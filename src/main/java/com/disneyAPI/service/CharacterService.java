package com.disneyAPI.service;

import com.disneyAPI.mapper.CharacterMapper;
import com.disneyAPI.repository.CharacterRepository;
import com.disneyAPI.repository.model.CharacterModel;
import com.disneyAPI.domain.Character;
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


}
