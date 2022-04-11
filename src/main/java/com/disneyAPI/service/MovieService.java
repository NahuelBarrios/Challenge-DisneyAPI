package com.disneyAPI.service;

import com.disneyAPI.domain.Movie;
import com.disneyAPI.exceptions.DisneyRequestException;
import com.disneyAPI.mapper.MovieMapper;
import com.disneyAPI.repository.CharacterRepository;
import com.disneyAPI.repository.MovieRepository;
import com.disneyAPI.repository.model.MovieModel;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;

public class MovieService {

    private final MovieRepository movieRepository;
    private final CharacterRepository characterRepository;

    public MovieService(MovieRepository movieRepository,
                        CharacterRepository characterRepository){
        this.movieRepository = movieRepository;
        this.characterRepository = characterRepository;
    }

    @Transactional
    public Movie createMovie(Movie movie){
        MovieModel movieModel = movieRepository.save(MovieMapper.mapDomainToModel(movie));
        return MovieMapper.mapModelToDomain(movieModel);
    }

    @Transactional
    public List<Movie> getAll() {
        List<MovieModel> movieModelList = movieRepository.findAll();
        return movieModelList.stream().map(MovieMapper::mapModelToDomain)
                .collect(Collectors.toList());
    }

    @Transactional
    public Movie getById(Integer id) throws DisneyRequestException {
        Optional<MovieModel> modelOptional = movieRepository.findById(id);
        if (!modelOptional.isEmpty()) {
            MovieModel movieModel = modelOptional.get();
            return MovieMapper.mapModelToDomain(movieModel);
        } else {
            throw new DisneyRequestException("Pelicula not found", "not.found", HttpStatus.NOT_FOUND);
        }
    }

    @Transactional
    public List<Movie> getTittle(String tittle) {
        List<MovieModel> characterModelList = movieRepository.findByTittle(tittle);
        return characterModelList.stream().map(MovieMapper::mapModelToDomain)
                .collect(Collectors.toList());
    }

    public List<Movie> getOrder(String order) {
        List<MovieModel> movieModels;
        if (order.equalsIgnoreCase("DESC")){
            movieModels = movieRepository.findAllByOrderByTittleDesc();
        } else {
             movieModels = movieRepository.findAllByOrderByTittleAsc();
        }
        return movieModels.stream().map(MovieMapper::mapModelToDomain)
                .collect(Collectors.toList());
    }
}
