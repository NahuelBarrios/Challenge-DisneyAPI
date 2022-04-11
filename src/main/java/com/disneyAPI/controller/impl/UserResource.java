package com.disneyAPI.controller.impl;

import com.disneyAPI.controller.UserController;
import com.disneyAPI.domain.User;
import com.disneyAPI.dtos.JwtDTO;
import com.disneyAPI.dtos.UserCreationDTO;
import com.disneyAPI.dtos.UserDTO;
import com.disneyAPI.dtos.UserLoginDTO;
import com.disneyAPI.dtos.UserUpdateDTO;
import com.disneyAPI.exceptions.DisneyRequestException;
import com.disneyAPI.mapper.UserMapper;
import com.disneyAPI.service.UserService;
import io.swagger.annotations.Api;
import java.util.List;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "UserResource", tags = {"Users"})
@RestController
public class UserResource implements UserController {

    private final UserService userService;

    public UserResource(UserService userService){
        this.userService = userService;
    }

    @Override
    public JwtDTO createUser (UserCreationDTO userCreationDTO){
        User userDomain = UserMapper.mapDtoCreationToDomain(userCreationDTO);
        userService.registerUser(userDomain);
        JwtDTO jwtDto = userService.generateAuthenticationToken(userDomain);
        return jwtDto;
    }

    @Override
    public JwtDTO loginUser(UserLoginDTO userLoginDTO){
        User userDomain = UserMapper.mapLoginDTOToDomain(userLoginDTO);
        UserMapper.mapDomainToDTO(userService.loginUser(userDomain));
        JwtDTO jwtDto = userService.generateAuthenticationToken(userDomain);
        return jwtDto;
    }

    @Override
    public List<UserDTO> findAll() {
        List<UserDTO> userDTOList = userService.getAll();
        return userDTOList;
    }

    @Override
    public void deleteUser(Integer id){
        userService.deleteUser(id);
    }

    @Override
    public UserDTO updateUser(Integer id, UserUpdateDTO userUpdateDTO) throws DisneyRequestException {
        User user = UserMapper.mapUpdateDtoToDomain(userUpdateDTO);
        UserDTO userDTO = UserMapper.mapDomainToDTO(userService.updateUser(id,user));
        return userDTO;
    }

}
