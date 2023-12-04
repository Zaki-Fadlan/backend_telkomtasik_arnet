package com.telkomtasik.arnet.dto.patrol;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.telkomtasik.arnet.model.Status;
import com.telkomtasik.arnet.model.patrol.SchedulePatroli;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DTOReportPatroli {

    @NotNull(message = "Schedule is Required")
    @JsonProperty("schedule")
    private SchedulePatroli schedulePatroli;

    @NotNull(message = "Status is Required")
    private Status status;

    @NotEmpty(message = "Keterangan is Required")
    private String keterangan;
}
