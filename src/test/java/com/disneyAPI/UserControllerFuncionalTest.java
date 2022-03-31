package com.disneyAPI;

import com.disneyAPI.dtos.ErrorDTO;
import com.disneyAPI.dtos.JwtDTO;
import com.disneyAPI.dtos.UserCreationDTO;
import com.disneyAPI.dtos.UserDTO;
import com.disneyAPI.dtos.UserLoginDTO;
import com.disneyAPI.dtos.UserUpdateDTO;
import com.disneyAPI.repository.UserRepository;
import com.disneyAPI.util.HeaderBuilder;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
public class UserControllerFuncionalTest {

    @Autowired
    private TestRestTemplate testRestTemplate;
    private String userControllerUrl;
    private HttpEntity<?> entity;

    @Autowired
    UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userControllerUrl = testRestTemplate.getRootUri() + "/auth";
    }

    @AfterEach
    void deleteAll() {
        userRepository.deleteAll();
    }

    @Test
    void testRegister_returnsResponseWithJwt() {
        //Given
        UserCreationDTO userCreationDTO = UserCreationDTO.builder()
                .name("nahuel")
                .lastName("barrios")
                .email("email@gmail.com")
                .password("password")
                .build();
        String endpointUrl = userControllerUrl + "/register";
        entity = new HttpEntity(userCreationDTO, null);
        //When
        ResponseEntity<JwtDTO> response = testRestTemplate.exchange(
                endpointUrl,
                HttpMethod.POST,
                entity,
                new ParameterizedTypeReference<>() {
                },
                Map.of()
        );
        //Then
        assertEquals(201, response.getStatusCode().value());
    }

    @Test
    void testRegister_returnsErrorDto() {
        UserCreationDTO userCreationDTO = UserCreationDTO.builder()
                .name("nahuel")
                .email("email@gmail.com")
                .password("password")
                .build();
        String endpointUrl = userControllerUrl + "/register";
        entity = new HttpEntity(userCreationDTO, null);

        ResponseEntity<JwtDTO> response = testRestTemplate.exchange(
                endpointUrl,
                HttpMethod.POST,
                entity,
                new ParameterizedTypeReference<>() {
                },
                Map.of()
        );

        assertEquals(400, response.getStatusCode().value());
    }

    @Test
    void testLogin_returnsResponseWithJwt() {
        UserLoginDTO userLoginDTO = UserLoginDTO.builder()
                .email("admin1@gmail.com")
                .password("passwordadmin")
                .build();
        String endpointUrl = userControllerUrl + "/login";
        entity = new HttpEntity(userLoginDTO, null);

        ResponseEntity<JwtDTO> response = testRestTemplate.exchange(
                endpointUrl,
                HttpMethod.POST,
                entity,
                new ParameterizedTypeReference<>() {
                },
                Map.of()
        );

        assertEquals(200, response.getStatusCode().value());
    }

    @Test
    void testLogin_returnsResponseErrorDto() {
        UserLoginDTO userLoginDTO = UserLoginDTO.builder()
                .email("bademail@gmail.com")
                .password("password")
                .build();
        String endpointUrl = userControllerUrl + "/login";
        entity = new HttpEntity(userLoginDTO, null);

        ResponseEntity<JwtDTO> response = testRestTemplate.exchange(
                endpointUrl,
                HttpMethod.POST,
                entity,
                new ParameterizedTypeReference<>() {
                },
                Map.of()
        );

        assertEquals(404, response.getStatusCode().value());
    }

    @Test
    void testFindAll_returnsListUserDTO(){
        //Given
        String endpointUrl = userControllerUrl+"/users";
        HttpHeaders headers = new HeaderBuilder()
                .withValidToken("admin1@gmail.com", 3600L)
                .build();
        entity = new HttpEntity(null, headers);
        //When
        ResponseEntity<List<UserDTO>> response = testRestTemplate.exchange(
                endpointUrl,
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<>() {
                },
                Map.of()
        );
        //Then
        assertEquals(200, response.getStatusCode().value());
    }

    @Test
    void testDelete_returnOK(){
        //Given
        String endpointUrl = userControllerUrl+"/{id}";
        HttpHeaders headers = new HeaderBuilder()
                .withValidToken("admin1@gmail.com", 3600L)
                .build();
        entity = new HttpEntity(null, headers);
        //When
        ResponseEntity<UserDTO> response = testRestTemplate.exchange(
                endpointUrl,
                HttpMethod.DELETE,
                entity,
                new ParameterizedTypeReference<>() {
                },
                Map.of("id",1)
        );
        //Then
        assertEquals(200, response.getStatusCode().value());
    }

    @Test
    void testDelete_returnErrorDto(){
        //Given
        String endpointUrl = userControllerUrl+"/{id}";
        HttpHeaders headers = new HeaderBuilder()
                .withValidToken("admin1@gmail.com", 3600L)
                .build();
        entity = new HttpEntity(null, headers);
        //When
        ResponseEntity<ErrorDTO> response = testRestTemplate.exchange(
                endpointUrl,
                HttpMethod.DELETE,
                entity,
                new ParameterizedTypeReference<>() {
                },
                Map.of("id",3)
        );
        //Then
        assertEquals(404, response.getStatusCode().value());
    }

    @Test
    void testUpdate_returnOk(){
        //Given
        UserUpdateDTO userUpdateDTO = UserUpdateDTO.builder()
                .firstName("walter")
                .lastName("barry")
                .email("admin1@gmail.com")
                .password("123").build();
        String endpointUrl = userControllerUrl+"/{id}";
        HttpHeaders headers = new HeaderBuilder()
                .withValidToken("admin1@gmail.com", 3600L)
                .build();
        entity = new HttpEntity(userUpdateDTO, headers);
        //When
        ResponseEntity<UserDTO> response = testRestTemplate.exchange(
                endpointUrl,
                HttpMethod.PUT,
                entity,
                new ParameterizedTypeReference<>() {
                },
                Map.of("id",2)
        );
        //Then
        assertEquals(200, response.getStatusCode().value());
    }

    @Test
    void testUpdate_returnErrorDTO(){
        //Given
        UserUpdateDTO userUpdateDTO = UserUpdateDTO.builder()
                .firstName("walter")
                .lastName("barry")
                .email("admin1@gmail.com")
                .password("123").build();
        String endpointUrl = userControllerUrl+"/{id}";
        HttpHeaders headers = new HeaderBuilder()
                .withValidToken("admin1@gmail.com", 3600L)
                .build();
        entity = new HttpEntity(userUpdateDTO, headers);
        //When
        ResponseEntity<UserDTO> response = testRestTemplate.exchange(
                endpointUrl,
                HttpMethod.PUT,
                entity,
                new ParameterizedTypeReference<>() {
                },
                Map.of("id",2)
        );
        //Then
        assertEquals(404, response.getStatusCode().value());
    }

    private void createUser(){
        UserCreationDTO userCreationDTO = UserCreationDTO.builder()
                .name("nahuel")
                .lastName("barrios")
                .email("email@gmail.com")
                .password("password")
                .build();
        String endpointUrl = userControllerUrl + "/register";
        entity = new HttpEntity(userCreationDTO, null);

        ResponseEntity<JwtDTO> response = testRestTemplate.exchange(
                endpointUrl,
                HttpMethod.POST,
                entity,
                new ParameterizedTypeReference<>() {
                },
                Map.of()
        );
        assertEquals(201, response.getStatusCode().value());
    }

}
