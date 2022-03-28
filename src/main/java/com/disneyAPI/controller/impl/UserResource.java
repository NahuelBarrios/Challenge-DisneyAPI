package com.disneyAPI.controller.impl;

import com.disneyAPI.controller.UserController;
import com.disneyAPI.domain.User;
import com.disneyAPI.dtos.ErrorDTO;
import com.disneyAPI.dtos.JwtDTO;
import com.disneyAPI.dtos.UserCreationDTO;
import com.disneyAPI.dtos.UserDTO;
import com.disneyAPI.dtos.UserLoginDTO;
import com.disneyAPI.dtos.UserUpdateDTO;
import com.disneyAPI.exceptions.UserNotFoundException;
import com.disneyAPI.mapper.UserMapper;
import com.disneyAPI.service.UserService;
import io.swagger.annotations.Api;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
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
    public void deleteUser(@PathVariable Integer id){
        userService.deleteUser(id);
    }

    @Override
    public UserDTO updateUser(Integer id, UserUpdateDTO userUpdateDTO) throws UserNotFoundException {
        User user = UserMapper.mapUpdateDtoToDomain(userUpdateDTO);
        UserDTO userDTO = UserMapper.mapDomainToDTO(userService.updateUser(id,user));
        return userDTO;
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorDTO> handleUserNotFoundExceptions(UserNotFoundException ex) {
        ErrorDTO userNotFound =
                ErrorDTO.builder()
                        .code(HttpStatus.NOT_FOUND)
                        .message(ex.getMessage()).build();
        return new ResponseEntity(userNotFound, HttpStatus.NOT_FOUND);

    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

}
