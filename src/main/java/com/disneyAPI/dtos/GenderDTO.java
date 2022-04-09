package com.disneyAPI.dtos;

import com.disneyAPI.repository.model.MovieModel;
import java.io.Serializable;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GenderDTO implements Serializable {
    private String name;
    private List<MovieModel> movies;

}
