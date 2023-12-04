package com.telkomtasik.arnet.service;

import com.telkomtasik.arnet.model.Status;

import java.util.Map;

public interface StatusService {
    Map createOne(Status status);

    Map getAll(int size, int page, String name);

    Map updateOne(Status status);

    Map removeById(Long id);
}
