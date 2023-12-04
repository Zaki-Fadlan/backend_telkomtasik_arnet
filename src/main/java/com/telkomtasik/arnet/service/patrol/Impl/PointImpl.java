package com.telkomtasik.arnet.service.patrol.Impl;

import com.telkomtasik.arnet.dto.ResponseData;
import com.telkomtasik.arnet.dto.ResponseMetaData;
import com.telkomtasik.arnet.dto.patrol.DTOPoint;
import com.telkomtasik.arnet.model.patrol.Point;
import com.telkomtasik.arnet.repository.patrol.PointRepo;
import com.telkomtasik.arnet.service.patrol.PointService;
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
public class PointImpl implements PointService {
    @Autowired
    private PointRepo pointRepo;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ResponseData responseData;

    @Override
    public Map createOne(DTOPoint dtoPoint) {
        Point data = modelMapper.map(dtoPoint, Point.class);
        Point check = pointRepo.findByCode(data.getCode());
        if (check == null) {
            pointRepo.save(data);
            return responseData.successResponse(dtoPoint, HttpStatus.CREATED.value());
        } else {
            List<String> errorMessages = new ArrayList<>();
            errorMessages.add("Data Conflict");
            return responseData.failResponse(errorMessages, HttpStatus.CONFLICT.value());
        }
    }

    @Override
    public Map getAll(int size, int page, String name) {
        Page<Point> list = null;
        Pageable show_data = PageRequest.of(page, size, Sort.by("id").ascending());
        if (name != null && !name.isEmpty()) {
            list = pointRepo.findByNameLike("%" + name + "%", show_data);
        } else {
            list = pointRepo.getAllData(show_data);
        }
        ResponseMetaData metaData = modelMapper.map(list, ResponseMetaData.class);

        return responseData.successResponseWithMeta(list.getContent(), metaData, HttpStatus.OK.value());
    }

    @Override
    public Map updateOne(Point point) {
        List<String> errorMessages = new ArrayList<>();
        Optional<Point> optionalCurrData = pointRepo.findById(point.getId());
        Point check = pointRepo.findByCode(point.getCode());

        if (optionalCurrData.isEmpty()) {
            errorMessages.add("Data Not Found");
            return responseData.failResponse(errorMessages, HttpStatus.NOT_FOUND.value());
        }
        if (check != null && point.getId() != check.getId()) {
            errorMessages.add("Data Conflict");
            return responseData.failResponse(errorMessages, HttpStatus.CONFLICT.value());
        }

        point.setUpdatedDate(new Date());
        pointRepo.save(point);
        DTOPoint viewNewData = modelMapper.map(point, DTOPoint.class);
        return responseData.successResponse(viewNewData, HttpStatus.OK.value());
    }

    @Override
    public Map removeById(UUID id) {
        Optional<Point> optionalCurrData = pointRepo.findById(id);

        if (optionalCurrData.isEmpty()) {
            List<String> errorMessages = new ArrayList<>();
            errorMessages.add("Data Not Found");
            return responseData.failResponse(errorMessages, HttpStatus.NOT_FOUND.value());
        }

        Point point = modelMapper.map(optionalCurrData, Point.class);

        point.setDeletedDate(new Date());
        pointRepo.save(point);

        DTOPoint viewNewData = modelMapper.map(point, DTOPoint.class);
        return responseData.successResponse(viewNewData, HttpStatus.OK.value());
    }
}
