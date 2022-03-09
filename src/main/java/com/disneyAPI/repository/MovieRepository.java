package com.disneyAPI.repository;

import com.disneyAPI.repository.model.MovieModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends CrudRepository<MovieModel,Integer> {
}
