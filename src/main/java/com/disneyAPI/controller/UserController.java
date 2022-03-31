package com.disneyAPI.controller;

import com.disneyAPI.dtos.JwtDTO;
import com.disneyAPI.dtos.UserCreationDTO;
import com.disneyAPI.dtos.UserDTO;
import com.disneyAPI.dtos.UserLoginDTO;
import com.disneyAPI.dtos.UserUpdateDTO;
import com.disneyAPI.exceptions.UserNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Tag(name = "Users", description = "Operations related to Users")
public interface UserController {

    @Operation(
            summary = "Register a new User",
            description = "To register, this endpoint must be accessed"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Register user",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = JwtDTO.class))
                    }),
            @ApiResponse(responseCode = "400",
                    description = "The fields must not be empty",
                    content = @Content),
            @ApiResponse(responseCode = "400",
                    description = "This email is in use",
                    content = @Content)
    })
    @PostMapping("/auth/register")
    @ResponseStatus(HttpStatus.CREATED)
    JwtDTO createUser(@Valid @RequestBody UserCreationDTO userCreationDTO);

    @Operation(
            summary = "User login",
            description = "To log in, you must access this endpoint"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "User login",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = JwtDTO.class))
                    }),
            @ApiResponse(responseCode = "400",
                    description = "The password is invalid",
                    content = @Content),
            @ApiResponse(responseCode = "404",
                    description = "User not found",
                    content = @Content)
    })
    @PostMapping("/auth/login")
    @ResponseStatus(HttpStatus.OK)
    JwtDTO loginUser(@Valid @RequestBody UserLoginDTO userLoginDTO);

    @Operation(
            summary = "Find all users",
            description = "To fetch all users, you must access this endpoint"
    )
    @ApiResponse(responseCode = "200",
            description = "Find all users",
            content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = List.class))
            })
    @GetMapping("/auth/users")
    @ResponseStatus(HttpStatus.OK)
    List<UserDTO> findAll();

    @Operation(summary = "Delete a User by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Delete User by id",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content)})
    @DeleteMapping("/auth/{id}")
    @ResponseStatus(HttpStatus.OK)
    void deleteUser(@PathVariable Integer id);

    @Operation(summary = "Update User by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Update User by id",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = UserDTO.class))}),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content)})
    @PutMapping("/auth/{id}")
    @ResponseStatus(HttpStatus.OK)
    UserDTO updateUser(@PathVariable Integer id,
                                              @Valid @RequestBody UserUpdateDTO userUpdateDTO) throws UserNotFoundException;

}
