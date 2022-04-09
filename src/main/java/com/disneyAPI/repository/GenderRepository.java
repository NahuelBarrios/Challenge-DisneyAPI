package com.disneyAPI.repository;

import com.disneyAPI.repository.model.GenderModel;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface GenderRepository extends CrudRepository<GenderModel,Integer> {
    List<GenderModel> findAll();
}
