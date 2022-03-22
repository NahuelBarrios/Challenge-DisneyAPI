package com.disneyAPI.repository;

import com.disneyAPI.repository.model.UserModel;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<UserModel,Integer> {
    UserModel findByEmail(String email);
    boolean existsByEmail(String email);
    List<UserModel> findAll();
}
