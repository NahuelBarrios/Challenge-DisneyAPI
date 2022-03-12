package com.disneyAPI.mapper;

import com.disneyAPI.domain.Movie;
import com.disneyAPI.dtos.MovieDTO;
import com.disneyAPI.dtos.MovieDTOCreation;
import com.disneyAPI.dtos.MovieDTOList;
import com.disneyAPI.repository.model.MovieModel;

public class MovieMapper {

    public static Movie mapModelToDomain(MovieModel movieModel){
        Movie movie = Movie.builder()
                .image(movieModel.getImage())
                .tittle(movieModel.getTittle())
                .creation(movieModel.getCreation())
                .calification(movieModel.getCalification()).build();
        return movie;
    }

    public static MovieModel mapDomainToModel(Movie movie){
        MovieModel movieModel = MovieModel.builder()
                .id(movie.getId())
                .image(movie.getImage())
                .tittle(movie.getTittle())
                .creation(movie.getCreation())
                .calification(movie.getCalification()).build();
        return movieModel;
    }

    public static Movie mapDTOCreationTODomain(MovieDTOCreation movieDTOCreation){
        Movie movie = Movie.builder()
                .image(movieDTOCreation.getImage())
                .tittle(movieDTOCreation.getTittle())
                .creation(movieDTOCreation.getCreation())
                .calification(movieDTOCreation.getCalification()).build();
        return movie;
    }

    public static MovieDTO mapDomainToDTO(Movie movie){
        MovieDTO movieDTO = MovieDTO.builder()
                .image(movie.getImage())
                .tittle(movie.getTittle())
                .creation(movie.getCreation())
                .calification(movie.getCalification()).build();
        return movieDTO;

    }

    public static MovieDTOList mapDomainToDTOList(Movie movie){
        MovieDTOList movieDTOList = MovieDTOList.builder()
                .image(movie.getImage())
                .tittle(movie.getTittle())
                .creation(movie.getCreation()).build();
        return movieDTOList;
    }
}
