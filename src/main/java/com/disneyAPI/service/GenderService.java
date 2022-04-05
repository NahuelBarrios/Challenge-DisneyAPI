package com.disneyAPI.service;

import com.disneyAPI.domain.Gender;
import com.disneyAPI.exceptions.GenderNotFoundException;
import com.disneyAPI.mapper.GenderMapper;
import com.disneyAPI.repository.GenderRepository;
import com.disneyAPI.repository.model.GenderModel;
import java.util.Optional;
import org.springframework.transaction.annotation.Transactional;

public class GenderService {

    private final GenderRepository genderRepository;

    public GenderService(GenderRepository genderRepository){
        this.genderRepository = genderRepository;
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
}
