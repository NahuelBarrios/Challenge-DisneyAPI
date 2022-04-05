package com.disneyAPI.mapper;

import com.disneyAPI.domain.Gender;
import com.disneyAPI.dtos.GenderDTO;
import com.disneyAPI.dtos.GenderDTOCreation;
import com.disneyAPI.dtos.GenderUpdateDTO;
import com.disneyAPI.repository.model.GenderModel;

public class GenderMapper {

    public static GenderModel mapDomainToModel(Gender gender){
        GenderModel genderModel = GenderModel.builder()
                .id(gender.getId())
                .name(gender.getName())
                .image(gender.getImage())
                .build();
        return genderModel;
    }

    public static Gender mapModelToDomain(GenderModel genderModel){
        Gender gender = Gender.builder()
                .id(genderModel.getId())
                .name(genderModel.getName())
                .image(genderModel.getImage())
                .build();
        return gender;
    }

    public static Gender mapCreationToDomain(GenderDTOCreation genderDTOCreation){
        Gender gender = Gender.builder()
                .name(genderDTOCreation.getName()).build();
        return gender;
    }

    public static GenderDTO mapDomainToDTO(Gender gender){
        GenderDTO genderDTO = GenderDTO.builder()
                .name(gender.getName())
                .movies(gender.getMovies()).build();
        return genderDTO;
    }

    public static Gender mapUpdateToDomain(GenderUpdateDTO genderUpdateDTO){
        Gender gender = Gender.builder()
                .name(genderUpdateDTO.getName())
                .build();
        return gender;
    }
}
