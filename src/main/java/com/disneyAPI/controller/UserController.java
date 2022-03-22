package com.disneyAPI.controller;

import com.disneyAPI.domain.User;
import com.disneyAPI.dtos.JwtDTO;
import com.disneyAPI.dtos.UserCreationDTO;
import com.disneyAPI.dtos.UserDTO;
import com.disneyAPI.mapper.UserMapper;
import com.disneyAPI.service.UserService;
import java.util.List;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @GetMapping("/auth/users")
    public ResponseEntity<List<UserDTO>> findAll() {
        List<UserDTO> userDTOList = userService.getAll();
        return ResponseEntity.ok(userDTOList);
    }
}
