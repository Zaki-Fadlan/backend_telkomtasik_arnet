package com.telkomtasik.arnet.dto.patrol;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.telkomtasik.arnet.model.patrol.CableType;
import com.telkomtasik.arnet.model.patrol.CheckPoint;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DTOLine {
    @NotEmpty(message = "Name is Required")
    private String name;

    @NotEmpty(message = "Code is Required")
    private String code;

    @NotNull(message = "Tipe kabel is Required")
    @JsonProperty("cable_type")
    private CableType cableType;

    @NotNull(message = "Check Point End is Required")
    @JsonProperty("start_checkpoint")
    private CheckPoint startCheckpoint;

    @NotNull(message = "Check Point End is Required")
    @JsonProperty("end_checkpoint")
    private CheckPoint endCheckpoint;
}
