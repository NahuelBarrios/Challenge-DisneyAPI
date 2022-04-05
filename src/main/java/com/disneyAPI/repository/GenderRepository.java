package com.disneyAPI.repository;

import com.disneyAPI.repository.model.GenderModel;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface GenderRepository extends CrudRepository<GenderModel,Integer> {
}
