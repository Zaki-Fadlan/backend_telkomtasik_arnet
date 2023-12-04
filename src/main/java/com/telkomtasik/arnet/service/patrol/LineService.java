package com.telkomtasik.arnet.service.patrol;

import com.telkomtasik.arnet.dto.patrol.DTOLine;
import com.telkomtasik.arnet.model.patrol.Line;

import java.util.Map;
import java.util.UUID;

public interface LineService {
    Map createOne(DTOLine dtoLine);

    Map getAll(int size, int page, String name);

    Map updateOne(Line line);

    Map removeById(UUID id);
}
