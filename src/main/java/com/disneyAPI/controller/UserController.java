package com.disneyAPI.controller;

import com.disneyAPI.domain.User;
import com.disneyAPI.dtos.ErrorDTO;
import com.disneyAPI.dtos.JwtDTO;
import com.disneyAPI.dtos.UserCreationDTO;
import com.disneyAPI.dtos.UserDTO;
import com.disneyAPI.dtos.UserLoginDTO;
import com.disneyAPI.exceptions.UserNotFoundException;
import com.disneyAPI.mapper.UserMapper;
import com.disneyAPI.service.UserService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("/auth/register")
    ResponseEntity<JwtDTO> createUser (@Valid @RequestBody UserCreationDTO userCreationDTO){
        User userDomain = UserMapper.mapDtoCreationToDomain(userCreationDTO);
        userService.registerUser(userDomain);
        JwtDTO jwtDto = userService.generateAuthenticationToken(userDomain);
        return ResponseEntity.ok(jwtDto);
    }

    @PostMapping("/auth/login")
    ResponseEntity<JwtDTO> loginUser(@Valid @RequestBody UserLoginDTO userLoginDTO){
        User userDomain = UserMapper.mapLoginDTOToDomain(userLoginDTO);
        UserMapper.mapDomainToDTO(userService.loginUser(userDomain));
        JwtDTO jwtDto = userService.generateAuthenticationToken(userDomain);
        return ResponseEntity.ok(jwtDto);
    }

    @GetMapping("/auth/users")
    public ResponseEntity<List<UserDTO>> findAll() {
        List<UserDTO> userDTOList = userService.getAll();
        return ResponseEntity.ok(userDTOList);
    }

    @DeleteMapping("/auth/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Integer id){
        userService.deleteUser(id);
        return new ResponseEntity(HttpStatus.OK);
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
