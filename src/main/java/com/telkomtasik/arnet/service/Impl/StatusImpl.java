package com.telkomtasik.arnet.service.Impl;

import com.telkomtasik.arnet.dto.ResponseData;
import com.telkomtasik.arnet.dto.ResponseMetaData;
import com.telkomtasik.arnet.dto.patrol.DTOCableType;
import com.telkomtasik.arnet.model.Status;
import com.telkomtasik.arnet.repository.StatusRepo;
import com.telkomtasik.arnet.service.StatusService;
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
public class StatusImpl implements StatusService {
    @Autowired
    private StatusRepo statusRepo;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ResponseData responseData;

    @Override
    public Map createOne(Status status) {
        statusRepo.save(status);
        return responseData.successResponse(status, HttpStatus.CREATED.value());
    }

    @Override
    public Map getAll(int size, int page, String name) {
        Page<Status> list = null;
        Pageable show_data = PageRequest.of(page, size, Sort.by("id").ascending());
        if (name != null && !name.isEmpty()) {
            list = statusRepo.findByNameLike("%" + name + "%", show_data);
        } else {
            list = statusRepo.getAllData(show_data);
        }
        ResponseMetaData metaData = modelMapper.map(list, ResponseMetaData.class);

        return responseData.successResponseWithMeta(list.getContent(), metaData, HttpStatus.OK.value());
    }


    @Override
    public Map updateOne(Status status) {
        List<String> errorMessages = new ArrayList<>();
        Optional<Status> optionalCurrData = statusRepo.findById(status.getId());

        if (optionalCurrData.isEmpty()) {
            errorMessages.add("Data Not Found");
            return responseData.failResponse(errorMessages, HttpStatus.NOT_FOUND.value());
        }
        Status newData = modelMapper.map(optionalCurrData, Status.class);
        newData.setName(status.getName());
        newData.setUpdatedDate(new Date());

        statusRepo.save(newData);
        DTOCableType viewNewData = modelMapper.map(status, DTOCableType.class);
        return responseData.successResponse(viewNewData, HttpStatus.OK.value());
    }

    @Override
    public Map removeById(Long id) {
        Optional<Status> optionalCurrData = statusRepo.findById(id);
        if (optionalCurrData.isEmpty()) {
            List<String> errorMessages = new ArrayList<>();
            errorMessages.add("Data Not Found");
            return responseData.failResponse(errorMessages, HttpStatus.NOT_FOUND.value());
        }

        Status statusData = modelMapper.map(optionalCurrData, Status.class);

        statusData.setDeletedDate(new Date());
        statusRepo.save(statusData);

        Status viewNewData = modelMapper.map(statusData, Status.class);
        return responseData.successResponse(viewNewData, HttpStatus.OK.value());
    }
}
