package com.disneyAPI.domain;

import com.disneyAPI.util.RoleName;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Role {
    private Integer id;
    private RoleName name;
    private String description;
    private LocalDateTime creationDate;
}
