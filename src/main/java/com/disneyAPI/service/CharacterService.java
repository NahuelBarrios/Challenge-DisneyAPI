package com.disneyAPI.service;

import com.disneyAPI.exceptions.CharacterNotFoundException;
import com.disneyAPI.exceptions.MovieNotFoundException;
import com.disneyAPI.mapper.CharacterMapper;
import com.disneyAPI.repository.CharacterRepository;
import com.disneyAPI.repository.MovieRepository;
import com.disneyAPI.repository.model.CharacterModel;
import com.disneyAPI.domain.Character;
import com.disneyAPI.repository.model.MovieModel;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.transaction.annotation.Transactional;

public class CharacterService {

    private final CharacterRepository characterRepository;
    private final MovieRepository movieRepository;

    public CharacterService(CharacterRepository characterRepository,
                            MovieRepository movieRepository){
        this.characterRepository = characterRepository;
        this.movieRepository = movieRepository;
    }

    @Transactional
    public Character createCharacter(Character character){
        CharacterModel characterModel = CharacterMapper.mapDomainToModel(character);
        Optional<MovieModel> movieModelOptional = movieRepository.findById(character.getId());
        if(movieModelOptional.isEmpty()){
            throw new MovieNotFoundException(String.format("Organization with ID: %s not found",character.getId()));
        }
        List<MovieModel> movieModelList = characterModel.getMovies();
        movieModelList.add(movieModelOptional.get());
        characterModel.setMovies(movieModelList);
        characterRepository.save(characterModel);
        return CharacterMapper.mapModelToDomain(characterModel);
    }

    @Transactional
    public Character updateCharacter(Integer id, Character character) throws CharacterNotFoundException{
        Optional<CharacterModel> characterModel = characterRepository.findById(id);
        if(characterModel.isEmpty()){
            throw new CharacterNotFoundException(
                    String.format("Character with ID: %s not found", id));
        }
        CharacterModel characterOld = characterModel.get();
        characterOld.setName(character.getName());
        characterOld.setAge(character.getAge());
        characterOld.setWeight(character.getWeight());
        characterOld.setMovies(character.getMovies());
        return CharacterMapper.mapModelToDomain(characterRepository.save(characterOld));
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
