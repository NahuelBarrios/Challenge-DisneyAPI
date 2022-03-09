package com.disneyAPI.dtos;

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
public class CharacterDTOCreation {
    private String image;
    private String name;
    private Integer age;
    private Double weight;
    private String history;
}
