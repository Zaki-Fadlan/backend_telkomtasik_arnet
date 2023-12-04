package com.telkomtasik.arnet.service.patrol;

import com.telkomtasik.arnet.dto.patrol.DTOReportPatroli;
import com.telkomtasik.arnet.model.patrol.ReportPatroli;

import java.util.Map;
import java.util.UUID;

public interface ReportPatroliService {
    Map createOne(DTOReportPatroli dtoReportPatroli);

    Map getAll(int size, int page, String name);

    Map updateOne(ReportPatroli reportPatroli);

    Map removeById(UUID id);
}
