package com.disneyAPI.exceptions;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiErrorDTO {
    private String code;
    private String message;
    private List<ApiErrorDetailDTO> details;
}

@Data
@NoArgsConstructor
class ApiErrorDetailDTO {
    private String field;
    private String error;
}