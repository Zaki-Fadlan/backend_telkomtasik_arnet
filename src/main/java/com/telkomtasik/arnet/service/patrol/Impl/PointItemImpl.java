package com.telkomtasik.arnet.service.patrol.Impl;

import com.telkomtasik.arnet.dto.ResponseData;
import com.telkomtasik.arnet.dto.ResponseMetaData;
import com.telkomtasik.arnet.dto.patrol.DTOPointItem;
import com.telkomtasik.arnet.model.patrol.Item;
import com.telkomtasik.arnet.model.patrol.Point;
import com.telkomtasik.arnet.model.patrol.PointItem;
import com.telkomtasik.arnet.repository.patrol.ItemRepo;
import com.telkomtasik.arnet.repository.patrol.PointItemRepo;
import com.telkomtasik.arnet.repository.patrol.PointRepo;
import com.telkomtasik.arnet.service.patrol.PointItemService;
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
public class PointItemImpl implements PointItemService {
    @Autowired
    private PointItemRepo pointItemRepo;
    @Autowired
    private PointRepo pointRepo;
    @Autowired
    private ItemRepo itemRepo;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ResponseData responseData;

    @Override
    public Map createOne(DTOPointItem dtoPointItem) {
        PointItem data = modelMapper.map(dtoPointItem, PointItem.class);

        Optional<Point> point = pointRepo.findById(dtoPointItem.getPoint().getId());
        Optional<Item> item = itemRepo.findById(dtoPointItem.getItem().getId());
        if (point.isEmpty() || item.isEmpty()) {
            List<String> errorMessages = new ArrayList<>();
            errorMessages.add("Data Not Found");
            return responseData.failResponse(errorMessages, HttpStatus.NOT_FOUND.value());
        }

        pointItemRepo.save(data);
        return responseData.successResponse(dtoPointItem, HttpStatus.CREATED.value());
    }

    @Override
    public Map getAll(int size, int page, String name) {
        Page<PointItem> list = null;
        Pageable show_data = PageRequest.of(page, size, Sort.by("id").ascending());
        list = pointItemRepo.getAllData(show_data);
        ResponseMetaData metaData = modelMapper.map(list, ResponseMetaData.class);
        return responseData.successResponseWithMeta(list.getContent(), metaData, HttpStatus.OK.value());
    }

    @Override
    public Map updateOne(PointItem pointItem) {

        Optional<PointItem> optionalCurrData = pointItemRepo.findById(pointItem.getId());
        if (optionalCurrData.isEmpty()) {
            List<String> errorMessages = new ArrayList<>();
            errorMessages.add("Data Not Found");
            return responseData.failResponse(errorMessages, HttpStatus.NOT_FOUND.value());
        }

        pointItem.setUpdatedDate(new Date());
        pointItemRepo.save(pointItem);
        DTOPointItem viewNewData = modelMapper.map(pointItem, DTOPointItem.class);
        return responseData.successResponse(viewNewData, HttpStatus.OK.value());
    }

    @Override
    public Map removeById(UUID id) {
        Optional<PointItem> optionalCurrData = pointItemRepo.findById(id);

        if (optionalCurrData.isEmpty()) {
            List<String> errorMessages = new ArrayList<>();
            errorMessages.add("Data Not Found");
            return responseData.failResponse(errorMessages, HttpStatus.NOT_FOUND.value());
        }

        PointItem point = modelMapper.map(optionalCurrData, PointItem.class);

        point.setDeletedDate(new Date());
        pointItemRepo.save(point);

        DTOPointItem viewNewData = modelMapper.map(point, DTOPointItem.class);
        return responseData.successResponse(viewNewData, HttpStatus.OK.value());
    }
}
