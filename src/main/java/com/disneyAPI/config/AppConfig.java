package com.disneyAPI.config;

import com.disneyAPI.repository.CharacterRepository;
import com.disneyAPI.repository.MovieRepository;
import com.disneyAPI.service.CharacterService;
import com.disneyAPI.service.MovieService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public CharacterService characterService(CharacterRepository characterRepository){
        return new CharacterService(characterRepository);
    }

    @Bean
    public MovieService movieService (MovieRepository movieRepository,
                                      CharacterRepository characterRepository){
        return new MovieService(movieRepository,characterRepository);
    }

}
