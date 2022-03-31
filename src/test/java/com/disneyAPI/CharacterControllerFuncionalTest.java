package com.disneyAPI;

import com.disneyAPI.dtos.CharacterDTO;
import com.disneyAPI.dtos.CharacterDTOCreation;
import com.disneyAPI.dtos.CharacterUpdateDTO;
import com.disneyAPI.dtos.ErrorDTO;
import com.disneyAPI.repository.CharacterRepository;
import com.disneyAPI.util.HeaderBuilder;
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
public class CharacterControllerFuncionalTest {

    @Autowired
    private TestRestTemplate testRestTemplate;
    private String characterControllerUrl;
    private HttpEntity<?> entity;

    @Autowired
    CharacterRepository charactersRepository;

    @BeforeEach
    void setUp() {
        characterControllerUrl = testRestTemplate.getRootUri() + "/characters";
    }

    @AfterEach
    void deleteAll() {
        charactersRepository.deleteAll();
    }

    @Test
    void testCreate_returnsResponseCharacterDTO() {
        //Given
        CharacterDTOCreation characterDTOCreation = CharacterDTOCreation.builder()
                .image("nahuel.jpg")
                .name("barrios")
                .age(25)
                .weight(12.4)
                .history("Hola esta es mi historia")
                .build();
        String endpointUrl = characterControllerUrl;
        HttpHeaders headers = new HeaderBuilder()
                .withValidToken("admin1@gmail.com", 3600L)
                .build();
        entity = new HttpEntity(characterDTOCreation, headers);
        //When
        ResponseEntity<CharacterDTO> response = testRestTemplate.exchange(
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
    void testCreate_returnsResponseErrorForbidden() {
        //Given
        CharacterDTOCreation characterDTOCreation = CharacterDTOCreation.builder()
                .image("nahuel.jpg")
                .name("barrios")
                .age(25)
                .weight(12.4)
                .history("Hola esta es mi historia")
                .build();
        String endpointUrl = characterControllerUrl;
        entity = new HttpEntity(characterDTOCreation, null);
        //When
        ResponseEntity<ErrorDTO> response = testRestTemplate.exchange(
                endpointUrl,
                HttpMethod.POST,
                entity,
                new ParameterizedTypeReference<>() {
                },
                Map.of()
        );
        //Then
        assertEquals(403, response.getStatusCode().value());
    }

    @Test
    void testUpdate_returnsResponseCharacterDTO() {
        //Given
        Integer id = createCharacter();
        CharacterUpdateDTO characterUpdateDTO = CharacterUpdateDTO.builder()
                .name("nahuel")
                .age(21)
                .weight(20.0)
                .build();
        String endpointUrl = characterControllerUrl + "/{id}";
        HttpHeaders headers = new HeaderBuilder()
                .withValidToken("admin1@gmail.com", 3600L)
                .build();
        entity = new HttpEntity(characterUpdateDTO, headers);
        //When
        ResponseEntity<CharacterDTO> response = testRestTemplate.exchange(
                endpointUrl,
                HttpMethod.PUT,
                entity,
                new ParameterizedTypeReference<>() {
                },
                Map.of("id",id)
        );
        //Then
        assertEquals(200, response.getStatusCode().value());
    }

    @Test
    void testUpdate_returnsResponseErrorDTO() {
        //Given
        CharacterUpdateDTO characterUpdateDTO = CharacterUpdateDTO.builder()
                .name("nahuel")
                .age(21)
                .weight(20.0)
                .build();
        String endpointUrl = characterControllerUrl + "/{id}";
        HttpHeaders headers = new HeaderBuilder()
                .withValidToken("admin1@gmail.com", 3600L)
                .build();
        entity = new HttpEntity(characterUpdateDTO, headers);
        //When
        ResponseEntity<ErrorDTO> response = testRestTemplate.exchange(
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

    @Test
    void testDelete_returnsResponseOk() {
        //Given
        Integer id = createCharacter();
        String endpointUrl = characterControllerUrl + "/{id}";
        HttpHeaders headers = new HeaderBuilder()
                .withValidToken("admin1@gmail.com", 3600L)
                .build();
        entity = new HttpEntity(null, headers);
        //When
        ResponseEntity<CharacterDTO> response = testRestTemplate.exchange(
                endpointUrl,
                HttpMethod.DELETE,
                entity,
                new ParameterizedTypeReference<>() {
                },
                Map.of("id",id)
        );
        //Then
        assertEquals(200, response.getStatusCode().value());
    }

    @Test
    void testDelete_returnsResponseErrorDTO() {
        //Given
        String endpointUrl = characterControllerUrl + "/{id}";
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
                Map.of("id",2)
        );
        //Then
        assertEquals(404, response.getStatusCode().value());
    }

    private Integer createCharacter() {
        //Given
        CharacterDTOCreation characterDTOCreation = CharacterDTOCreation.builder()
                .image("nahuel.jpg")
                .name("barrios")
                .age(25)
                .weight(12.4)
                .history("Hola esta es mi historia")
                .build();
        String endpointUrl = characterControllerUrl;
        HttpHeaders headers = new HeaderBuilder()
                .withValidToken("admin1@gmail.com", 3600L)
                .build();
        entity = new HttpEntity(characterDTOCreation, headers);
        //When
        ResponseEntity<CharacterDTO> response = testRestTemplate.exchange(
                endpointUrl,
                HttpMethod.POST,
                entity,
                new ParameterizedTypeReference<>() {
                },
                Map.of()
        );
        //Then
        assertEquals(201, response.getStatusCode().value());
        return response.getBody().getId();
    }

}
