package com.disneyAPI.repository;

import com.disneyAPI.repository.model.RoleModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends CrudRepository<RoleModel,Integer> {
    RoleModel findByName(String name);
}
