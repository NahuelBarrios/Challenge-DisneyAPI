package com.disneyAPI.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CharacterUpdateDTO {
    private String name;
    private Integer age;
    private Double weight;
    private Integer movie;
}
