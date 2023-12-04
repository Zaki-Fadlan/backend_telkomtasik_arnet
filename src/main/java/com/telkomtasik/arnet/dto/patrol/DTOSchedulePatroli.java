package com.telkomtasik.arnet.dto.patrol;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.telkomtasik.arnet.model.patrol.PointItem;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
public class DTOSchedulePatroli {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @JsonProperty("start_date")
    @NotNull(message = "start date is Required")
    private Date startDate;

    @JsonProperty("end_date")
    @NotNull(message = "start date is Required")
    private Date endDate;

    @JsonProperty("point_item")
    @NotNull(message = "Item And Point is Required")
    private PointItem pointItem;
}
