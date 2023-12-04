package com.telkomtasik.arnet.service.patrol.Impl;

import com.telkomtasik.arnet.dto.ResponseData;
import com.telkomtasik.arnet.dto.ResponseMetaData;
import com.telkomtasik.arnet.dto.patrol.DTOCheckpoint;
import com.telkomtasik.arnet.model.patrol.CheckPoint;
import com.telkomtasik.arnet.repository.patrol.CheckPointRepo;
import com.telkomtasik.arnet.service.patrol.CheckPointService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CheckPointImpl implements CheckPointService {
    @Autowired
    private CheckPointRepo checkPointRepo;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ResponseData responseData;

    @Override
    public Map createOne(DTOCheckpoint dtoCheckpoint) {
        CheckPoint data = modelMapper.map(dtoCheckpoint, CheckPoint.class);
        CheckPoint check = checkPointRepo.findByCode(data.getCode());
        if (check == null) {
            checkPointRepo.save(data);
            return responseData.successResponse(dtoCheckpoint, HttpStatus.CREATED.value());
        } else {
            List<String> errorMessages = new ArrayList<>();
            errorMessages.add("Data Conflict");
            return responseData.failResponse(errorMessages, HttpStatus.CONFLICT.value());
        }
    }

    @Override
    public Map getAll(int size, int page, String name) {
        Page<CheckPoint> list = null;
        Pageable show_data = PageRequest.of(page, size, Sort.by("id").ascending());
        if (name != null && !name.isEmpty()) {
            list = checkPointRepo.findByNameLike("%" + name + "%", show_data);
        } else {
            list = checkPointRepo.getAllData(show_data);
        }
        ResponseMetaData metaData = modelMapper.map(list, ResponseMetaData.class);

        return responseData.successResponseWithMeta(list.getContent(), metaData, HttpStatus.OK.value());
    }

    @Override
    public Map updateOne(CheckPoint checkPoint) {
        List<String> errorMessages = new ArrayList<>();
        Optional<CheckPoint> optionalCurrData = checkPointRepo.findById(checkPoint.getId());
        CheckPoint check = checkPointRepo.findByCode(checkPoint.getCode());

        if (optionalCurrData.isEmpty()) {
            errorMessages.add("Data Not Found");
            return responseData.failResponse(errorMessages, HttpStatus.NOT_FOUND.value());
        }
        if (check != null && checkPoint.getId() != check.getId()) {
            errorMessages.add("Data Conflict");
            return responseData.failResponse(errorMessages, HttpStatus.CONFLICT.value());
        }

        checkPoint.setUpdatedDate(new Date());
        checkPointRepo.save(checkPoint);
        DTOCheckpoint viewNewData = modelMapper.map(checkPoint, DTOCheckpoint.class);
        return responseData.successResponse(viewNewData, HttpStatus.OK.value());
    }

    @Override
    public Map removeById(UUID id) {
        Optional<CheckPoint> optionalCurrData = checkPointRepo.findById(id);

        if (optionalCurrData.isEmpty()) {
            List<String> errorMessages = new ArrayList<>();
            errorMessages.add("Data Not Found");
            return responseData.failResponse(errorMessages, HttpStatus.NOT_FOUND.value());
        }

        CheckPoint checkPoint = modelMapper.map(optionalCurrData, CheckPoint.class);

        checkPoint.setDeletedDate(new Date());
        checkPointRepo.save(checkPoint);

        DTOCheckpoint viewNewData = modelMapper.map(checkPoint, DTOCheckpoint.class);
        return responseData.successResponse(viewNewData, HttpStatus.OK.value());
    }
}

