package com.disneyAPI.config;

import com.disneyAPI.repository.CharacterRepository;
import com.disneyAPI.repository.MovieRepository;
import com.disneyAPI.repository.RoleRepository;
import com.disneyAPI.repository.UserRepository;
import com.disneyAPI.security.JwtProvider;
import com.disneyAPI.security.UserDetailsServiceImpl;
import com.disneyAPI.service.CharacterService;
import com.disneyAPI.service.MovieService;
import com.disneyAPI.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AppConfig {

    @Bean
    public CharacterService characterService(CharacterRepository characterRepository,
                                             MovieRepository movieRepository){
        return new CharacterService(characterRepository,movieRepository);
    }

    @Bean
    public MovieService movieService (MovieRepository movieRepository,
                                      CharacterRepository characterRepository){
        return new MovieService(movieRepository,characterRepository);
    }

    @Bean
    public UserService userService (UserRepository userRepository, RoleRepository roleRepository,
                                    PasswordEncoder passwordEncoder, JwtProvider jwtProvider,
                                    AuthenticationManager authenticationManager){
        return new UserService(userRepository,roleRepository,passwordEncoder,jwtProvider,authenticationManager);
    }

    @Bean
    public UserDetailsServiceImpl userDetailsServiceImpl(UserRepository userRepository) {
        return new UserDetailsServiceImpl(userRepository);
    }

    @Bean
    public JwtProvider jwtProvider(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.expiration}") int expiration) {
        return new JwtProvider(secret, expiration);
    }
}
