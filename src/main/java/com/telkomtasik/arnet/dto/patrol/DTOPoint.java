package com.telkomtasik.arnet.dto.patrol;

import com.telkomtasik.arnet.model.patrol.Line;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DTOPoint {
    @NotNull(message = "Line is Required")
    private Line ruas;
    @NotEmpty(message = "Name is Required")
    private String name;
    @NotEmpty(message = "Code is Required")
    private String code;
    @NotEmpty(message = "Latitude is Required")
    private String latitude;
    @NotEmpty(message = "Longitude is Required")
    private String longitude;
}
