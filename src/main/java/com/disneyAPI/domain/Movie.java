package com.disneyAPI.domain;

import com.disneyAPI.repository.model.CharacterModel;
import java.util.Date;
import java.util.List;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Movie {
    private Integer id;
    private String image;
    private String tittle;
    private Date creation;
    private Integer calification;
    private List<CharacterModel> characterModel;
}
