package com.telkomtasik.arnet.service.patrol;

import com.telkomtasik.arnet.dto.patrol.DTOPointItem;
import com.telkomtasik.arnet.model.patrol.PointItem;

import java.util.Map;
import java.util.UUID;

public interface PointItemService {
    Map createOne(DTOPointItem dtoPointItem);

    Map getAll(int size, int page, String name);

    Map updateOne(PointItem pointItem);

    Map removeById(UUID id);
}
