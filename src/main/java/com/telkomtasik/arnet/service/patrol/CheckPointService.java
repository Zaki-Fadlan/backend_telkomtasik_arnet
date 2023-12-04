package com.telkomtasik.arnet.service.patrol;

import com.telkomtasik.arnet.dto.patrol.DTOCheckpoint;
import com.telkomtasik.arnet.model.patrol.CheckPoint;

import java.util.Map;
import java.util.UUID;

public interface CheckPointService {
    Map createOne(DTOCheckpoint dtoCheckpoint);

    Map getAll(int size, int page, String name);

    Map updateOne(CheckPoint checkPoint);

    Map removeById(UUID id);
}
