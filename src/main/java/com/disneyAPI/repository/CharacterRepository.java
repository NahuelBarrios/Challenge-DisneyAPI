package com.disneyAPI.repository;

import com.disneyAPI.repository.model.CharacterModel;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CharacterRepository extends CrudRepository<CharacterModel,Integer> {
        List<CharacterModel> findAll();
        List<CharacterModel> findByName(String name);
        List<CharacterModel> findByAge(Integer age);
}
