package com.disneyAPI.dtos;

import com.disneyAPI.repository.model.MovieModel;
import java.io.Serializable;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CharacterDTO implements Serializable {
    private Integer id;
    private String image;
    private String name;
    private Integer age;
    private Double weight;
    private String history;
    private List<MovieModel> movieModel;
}
