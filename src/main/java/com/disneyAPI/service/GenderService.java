package com.disneyAPI.service;

import com.disneyAPI.domain.Character;
import com.disneyAPI.domain.Gender;
import com.disneyAPI.exceptions.CharacterNotFoundException;
import com.disneyAPI.exceptions.GenderNotFoundException;
import com.disneyAPI.mapper.CharacterMapper;
import com.disneyAPI.mapper.GenderMapper;
import com.disneyAPI.repository.GenderRepository;
import com.disneyAPI.repository.MovieRepository;
import com.disneyAPI.repository.model.CharacterModel;
import com.disneyAPI.repository.model.GenderModel;
import com.disneyAPI.repository.model.MovieModel;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.transaction.annotation.Transactional;

public class GenderService {

    private final GenderRepository genderRepository;
    private final MovieRepository movieRepository;

    public GenderService(GenderRepository genderRepository,MovieRepository movieRepository){
        this.genderRepository = genderRepository;
        this.movieRepository = movieRepository;
    }

    @Transactional
    public Gender createGender(Gender gender){
        GenderModel genderModel = GenderMapper.mapDomainToModel(gender);
        genderRepository.save(genderModel);
        return GenderMapper.mapModelToDomain(genderModel);
    }

    @Transactional
    public Gender updateGender(Integer id, Gender gender) throws GenderNotFoundException {
        Optional<GenderModel> genderModelOptional= genderRepository.findById(id);
        if(genderModelOptional.isEmpty()){
            throw new GenderNotFoundException(String.format("Character with ID: %s not found", id));
        }
        GenderModel genderModel = genderModelOptional.get();
        genderModel.setName(gender.getName());
        return GenderMapper.mapModelToDomain(genderRepository.save(genderModel));
    }

    @Transactional
    public List<Gender> getAll(){
        List<GenderModel> genderModelList = genderRepository.findAll();
        return genderModelList.stream().map(GenderMapper::mapModelToDomain)
                .collect(Collectors.toList());
    }

    @Transactional
    public Gender updateMovie(Integer idGender, Integer idMovie) throws GenderNotFoundException {
        Optional<GenderModel> genderModelOptional = genderRepository.findById(idGender);
        if(genderModelOptional.isEmpty()){
            throw new GenderNotFoundException(
                    String.format("Gender with ID: %s not found", idGender));
        }
        genderModelOptional.get().setMovies(loadMovie(genderModelOptional.get().getMovies(),idMovie));
        genderRepository.save(genderModelOptional.get());
        return GenderMapper.mapModelToDomain(genderModelOptional.get());
    }

    private List<MovieModel> loadMovie(List<MovieModel> movieModelList, Integer idMovie) throws GenderNotFoundException{
        List<MovieModel> auxList = movieModelList;
        for(MovieModel movieModel : auxList){
            if(movieModel.getId().equals(idMovie)){
                throw new GenderNotFoundException(String.format("Ya se encuentra en la lista", idMovie));
            }
        }
        MovieModel movieModelAux = movieRepository.findById(Integer.valueOf(idMovie)).get();
        auxList.add(movieModelAux);
        return auxList;
    }
}
