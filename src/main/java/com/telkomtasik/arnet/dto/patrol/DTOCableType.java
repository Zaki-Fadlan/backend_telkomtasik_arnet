package com.telkomtasik.arnet.dto.patrol;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class DTOCableType {
    @NotEmpty(message = "Name is Required")
    private String name;
    @NotEmpty(message = "Code is Required")
    private String code;
}
