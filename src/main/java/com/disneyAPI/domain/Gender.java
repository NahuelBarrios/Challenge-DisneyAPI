package com.disneyAPI.domain;

import com.disneyAPI.repository.model.MovieModel;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Gender {
    private Integer id;
    private String name;
    private String image;
    private List<MovieModel> movies;
}
