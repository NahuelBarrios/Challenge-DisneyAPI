package com.disneyAPI.controller.impl;

import com.disneyAPI.controller.MovieController;
import com.disneyAPI.domain.Movie;
import com.disneyAPI.dtos.ErrorDTO;
import com.disneyAPI.dtos.MovieDTO;
import com.disneyAPI.dtos.MovieDTOCreation;
import com.disneyAPI.dtos.MovieDTOList;
import com.disneyAPI.exceptions.MovieNotFoundException;
import com.disneyAPI.mapper.MovieMapper;
import com.disneyAPI.service.MovieService;
import io.swagger.annotations.Api;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "MovieResource", tags = {"Movies"})
@RestController
public class MovieResource implements MovieController {

    private final MovieService movieService;

    public MovieResource(MovieService movieService){
        this.movieService = movieService;
    }

    @Override
    public MovieDTO createMovie(MovieDTOCreation movieDTOCreation){
        Movie movieDomain = MovieMapper.mapDTOCreationTODomain(movieDTOCreation);
        MovieDTO movieDTO = MovieMapper.mapDomainToDTO(movieService.createMovie(movieDomain));
        return movieDTO;
    }

    @Override
    public List<MovieDTOList> getAll(){
        List<MovieDTOList> movieDTOList = movieService.getAll()
                .stream().map(MovieMapper::mapDomainToDTOList)
                .collect(Collectors.toList());
        return movieDTOList;
    }

    @Override
    public MovieDTO getDetailsMovie(Integer id) throws MovieNotFoundException {
        return MovieMapper.mapDomainToDTO(movieService.getById(id));
    }

    @Override
    public List<MovieDTO> getNameMovie(String name) {
        List<MovieDTO> movieDTOList = movieService.getTittle(name)
                .stream().map(MovieMapper::mapDomainToDTO)
                .collect(Collectors.toList());
        return movieDTOList;
    }

    @Override
    public List<MovieDTO> getMoviesOrder(String order) throws MovieNotFoundException{

        if (order.equalsIgnoreCase("ASC") || order.equalsIgnoreCase("DESC")){
            List<MovieDTO> movieDTOList = movieService.getOrder(order)
                    .stream().map(MovieMapper::mapDomainToDTO)
                    .collect(Collectors.toList());
            return movieDTOList;
        }
        else{
            throw new MovieNotFoundException(
                    String.format("List Movie Error"));
        }
    }

    @ExceptionHandler(MovieNotFoundException.class)
    public ResponseEntity<ErrorDTO> handleMovieNotFoundExceptions(MovieNotFoundException ex) {
        ErrorDTO movieNotFound = ErrorDTO.builder()
                .code(HttpStatus.NOT_FOUND)
                .message(ex.getMessage()).build();
        return new ResponseEntity(movieNotFound, HttpStatus.NOT_FOUND);
    }
}
