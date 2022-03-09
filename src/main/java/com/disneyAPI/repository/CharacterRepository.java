package com.disneyAPI.repository;

import com.disneyAPI.repository.model.CharacterModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CharacterRepository extends CrudRepository <CharacterModel,Integer>{
}
