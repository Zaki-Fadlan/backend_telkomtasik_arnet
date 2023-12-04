package com.telkomtasik.arnet.service.patrol;

import com.telkomtasik.arnet.dto.patrol.DTOPoint;
import com.telkomtasik.arnet.model.patrol.Point;

import java.util.Map;
import java.util.UUID;

public interface PointService {
    Map createOne(DTOPoint dtoPoint);

    Map getAll(int size, int page, String name);

    Map updateOne(Point point);

    Map removeById(UUID id);
}
