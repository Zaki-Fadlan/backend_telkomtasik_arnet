package com.telkomtasik.arnet.service.patrol;

import com.telkomtasik.arnet.dto.patrol.DTOItem;
import com.telkomtasik.arnet.model.patrol.Item;

import java.util.Map;
import java.util.UUID;

public interface ItemService {
    Map createOne(DTOItem dtoItem);

    Map getAll(int size, int page, String name);

    Map updateOne(Item item);

    Map removeById(UUID id);
}
