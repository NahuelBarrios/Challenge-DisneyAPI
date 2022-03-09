package com.disneyAPI.config;

import com.disneyAPI.repository.CharacterRepository;
import com.disneyAPI.service.CharacterService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public CharacterService characterService(CharacterRepository characterRepository){
        return new CharacterService(characterRepository);
    }

}
