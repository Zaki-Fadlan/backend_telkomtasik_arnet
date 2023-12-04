package com.telkomtasik.arnet.service.patrol;

import com.telkomtasik.arnet.dto.patrol.DTOCableType;
import com.telkomtasik.arnet.model.patrol.CableType;

import java.util.Map;


public interface CableTypeService {
    Map createOne(DTOCableType dtoCableType);

    Map getAll(int size, int page, String name);

    Map updateOne(CableType cableType);

    Map removeById(Long id);

}
