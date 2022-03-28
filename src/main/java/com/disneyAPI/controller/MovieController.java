package com.disneyAPI.controller;

import com.disneyAPI.domain.Movie;
import com.disneyAPI.dtos.ErrorDTO;
import com.disneyAPI.dtos.MovieDTO;
import com.disneyAPI.dtos.MovieDTOCreation;
import com.disneyAPI.dtos.MovieDTOList;
import com.disneyAPI.exceptions.MovieNotFoundException;
import com.disneyAPI.mapper.MovieMapper;
import com.disneyAPI.service.MovieService;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MovieController {

    private final MovieService movieService;

    public MovieController(MovieService movieService){
        this.movieService = movieService;
    }

    @PostMapping("/movies")
    public ResponseEntity<MovieDTO> createMovie(@Valid @RequestBody MovieDTOCreation movieDTOCreation){
        Movie movieDomain = MovieMapper.mapDTOCreationTODomain(movieDTOCreation);
        MovieDTO movieDTO = MovieMapper.mapDomainToDTO(movieService.createMovie(movieDomain));
        return ResponseEntity.ok(movieDTO);
    }

    @GetMapping("/movies")
    public ResponseEntity<List<MovieDTOList>> getAll(){
        List<MovieDTOList> movieDTOList = movieService.getAll()
                .stream().map(MovieMapper::mapDomainToDTOList)
                .collect(Collectors.toList());
        return ResponseEntity.ok(movieDTOList);
    }

    @GetMapping("/movies/{id}")
    public ResponseEntity<MovieDTO> getDetailsMovie(@PathVariable Integer id) throws MovieNotFoundException {
        return ResponseEntity.ok(MovieMapper.mapDomainToDTO(movieService.getById(id)));
    }

    @GetMapping(value = "/movies", params = "name")
    public ResponseEntity<List<MovieDTO>> getNameMovie(@RequestParam String name) {
        List<MovieDTO> movieDTOList = movieService.getTittle(name)
                .stream().map(MovieMapper::mapDomainToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(movieDTOList);
    }

    @GetMapping(value = "/movies", params = "order")
    public ResponseEntity<List<MovieDTO>> getMoviesOrder(@RequestParam(defaultValue = "ASC") String order) throws MovieNotFoundException{

        if (order.equalsIgnoreCase("ASC") || order.equalsIgnoreCase("DESC")){
            List<MovieDTO> movieDTOList = movieService.getOrder(order)
                    .stream().map(MovieMapper::mapDomainToDTO)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(movieDTOList);
        }
        else{
            return ResponseEntity.badRequest().build();
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
