package com.disneyAPI.repository;

import com.disneyAPI.repository.model.MovieModel;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends CrudRepository<MovieModel,Integer> {
    List<MovieModel> findAll();
    List<MovieModel> findByTittle(String tittle);
    List<MovieModel> findAllByOrderByTittleDesc();
    List<MovieModel> findAllByOrderByTittleAsc();

}
