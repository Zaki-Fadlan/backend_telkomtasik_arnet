package com.telkomtasik.arnet.service.patrol;

import com.telkomtasik.arnet.dto.patrol.DTOSchedulePatroli;
import com.telkomtasik.arnet.model.patrol.SchedulePatroli;

import java.util.Map;
import java.util.UUID;

public interface SchedulePatroliService {
    Map createOne(DTOSchedulePatroli schedulePatroli);

    Map getAll(int size, int page, String name);

    Map updateOne(SchedulePatroli schedulePatroli);

    Map removeById(UUID id);
}
