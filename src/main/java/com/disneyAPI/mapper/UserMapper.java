package com.disneyAPI.mapper;

import com.disneyAPI.domain.User;
import com.disneyAPI.dtos.UserCreationDTO;
import com.disneyAPI.dtos.UserDTO;
import com.disneyAPI.repository.model.UserModel;

public class UserMapper {
    public static User mapModelToDomain(UserModel userModel) {
        User userDomain = User.builder()
                .firstName(userModel.getFirstName())
                .lastName(userModel.getLastName())
                .email(userModel.getEmail())
                .password(userModel.getPassword())
                .photo(userModel.getPhoto())
                .role(RoleMapper.mapModelToDomain(userModel.getRole())).build();
        return userDomain;
    }

    public static UserModel mapDomainToModel(User userDomain) {
        UserModel userModel = UserModel.builder()
                .firstName(userDomain.getFirstName())
                .lastName(userDomain.getLastName())
                .email(userDomain.getEmail())
                .password(userDomain.getPassword())
                .photo(userDomain.getPhoto())
                .role(RoleMapper.mapDomainToModel(userDomain.getRole())).build();
        return userModel;
    }

    public static UserDTO mapDomainToDTO(User userDomain) {
        UserDTO userDTO = UserDTO.builder().
                lastName(userDomain.getLastName()).
                firstName(userDomain.getFirstName()).
                id(userDomain.getId()).
                creationDate(userDomain.getCreationDate()).
                email(userDomain.getEmail()).
                photo(userDomain.getPhoto()).build();
        return userDTO;
    }

    public static User mapDtoCreationToDomain(UserCreationDTO userCreationDTO) {
        User userDomain = User.builder().
                email(userCreationDTO.getEmail()).
                firstName(userCreationDTO.getName()).
                lastName(userCreationDTO.getLastName()).
                password(userCreationDTO.getPassword()).build();
        return userDomain;
    }

}
