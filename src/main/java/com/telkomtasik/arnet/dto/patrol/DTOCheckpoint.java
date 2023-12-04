package com.telkomtasik.arnet.dto.patrol;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class DTOCheckpoint {
    @NotEmpty(message = "Name is Required")
    private String name;
    @NotEmpty(message = "Code is Required")
    private String code;
    @NotEmpty(message = "Latitude is Required")
    private String latitude;
    @NotEmpty(message = "Longitude is Required")
    private String longitude;
}
