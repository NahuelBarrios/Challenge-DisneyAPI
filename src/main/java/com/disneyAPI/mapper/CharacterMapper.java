package com.disneyAPI.mapper;

import com.disneyAPI.dtos.CharacterDTO;
import com.disneyAPI.dtos.CharacterDTOCreation;
import com.disneyAPI.dtos.CharacterDTOList;
import com.disneyAPI.dtos.CharacterUpdateDTO;
import com.disneyAPI.repository.model.CharacterModel;
import com.disneyAPI.domain.Character;
import java.util.stream.Collectors;

public class CharacterMapper {

    public static Character mapModelToDomain(CharacterModel characterModel){
        Character character = Character.builder()
                .id(characterModel.getId())
                .image(characterModel.getImage())
                .name(characterModel.getName())
                .age(characterModel.getAge())
                .weight(characterModel.getWeight())
                .history(characterModel.getHistory())
                .movies(characterModel.getMovies())
                .build();
        return character;
    }

    public static CharacterModel mapDomainToModel(Character character){
        CharacterModel characterModel = CharacterModel.builder()
                .id(character.getId())
                .image(character.getImage())
                .name(character.getName())
                .age(character.getAge())
                .weight(character.getWeight())
                .history(character.getHistory())
                .movies(character.getMovies())
                .build();
        return characterModel;
    }

    public static Character mapDTOCreationTODomain(CharacterDTOCreation characterDTOCreation){
        Character character = Character.builder()
                .image(characterDTOCreation.getImage())
                .name(characterDTOCreation.getName())
                .age(characterDTOCreation.getAge())
                .weight(characterDTOCreation.getWeight())
                .history(characterDTOCreation.getHistory()).build();
        return character;
    }

    public static CharacterDTO mapDomainToDTO (Character character){
        CharacterDTO characterDTO = CharacterDTO.builder()
                .id(character.getId())
                .image(character.getImage())
                .name(character.getName())
                .age(character.getAge())
                .weight(character.getWeight())
                .history(character.getHistory())
                .movieModel(character.getMovies()).build();
        return characterDTO;
    }

    public static CharacterDTOList mapDomainToDTOList(Character character){
        CharacterDTOList characterDTOList = CharacterDTOList.builder()
                .name(character.getName())
                .image(character.getImage()).build();
        return characterDTOList;
    }

    public static Character mapUpdateDtoToDomain(CharacterUpdateDTO characterUpdateDTO){
        Character character = Character.builder()
                .name(characterUpdateDTO.getName())
                .age(characterUpdateDTO.getAge())
                .weight(characterUpdateDTO.getWeight())
                .build();
        return character;
    }
}
