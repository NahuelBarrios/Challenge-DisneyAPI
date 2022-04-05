package com.disneyAPI.dtos;

import com.disneyAPI.repository.model.GenderModel;
import com.disneyAPI.repository.model.MovieModel;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GenderDTO {
    private String name;
    private List<MovieModel> movies;

}
