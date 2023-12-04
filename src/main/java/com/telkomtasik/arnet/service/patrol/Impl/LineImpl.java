package com.telkomtasik.arnet.service.patrol.Impl;

import com.telkomtasik.arnet.dto.ResponseData;
import com.telkomtasik.arnet.dto.ResponseMetaData;
import com.telkomtasik.arnet.dto.patrol.DTOLine;
import com.telkomtasik.arnet.model.patrol.CableType;
import com.telkomtasik.arnet.model.patrol.CheckPoint;
import com.telkomtasik.arnet.model.patrol.Line;
import com.telkomtasik.arnet.repository.patrol.CableTypeRepo;
import com.telkomtasik.arnet.repository.patrol.CheckPointRepo;
import com.telkomtasik.arnet.repository.patrol.LineRepo;
import com.telkomtasik.arnet.service.patrol.LineService;
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
public class LineImpl implements LineService {
    @Autowired
    private LineRepo lineRepo;
    @Autowired
    private CheckPointRepo checkPointRepo;
    @Autowired
    private CableTypeRepo cableTypeRepo;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ResponseData responseData;

    @Override
    public Map createOne(DTOLine dtoLine) {
        List<String> errorMessages = new ArrayList<>();

        Optional<CableType> checkCableType = cableTypeRepo.findById(dtoLine.getCableType().getId());
        Optional<CheckPoint> checkPointStart = checkPointRepo.findById(dtoLine.getStartCheckpoint().getId());
        Optional<CheckPoint> checkPointEnd = checkPointRepo.findById(dtoLine.getEndCheckpoint().getId());

        if (dtoLine.getStartCheckpoint() == dtoLine.getEndCheckpoint()) {
            errorMessages.add("Start Check Point must different with End Check Point");
            return responseData.failResponse(errorMessages, HttpStatus.BAD_REQUEST.value());
        }
        if (checkCableType.isEmpty()) {
            errorMessages.add("Cable Type Not Found");
            return responseData.failResponse(errorMessages, HttpStatus.NOT_FOUND.value());
        }
        if (checkPointStart.isEmpty()) {
            errorMessages.add("Check Point Not Found");
            return responseData.failResponse(errorMessages, HttpStatus.NOT_FOUND.value());
        }
        if (checkPointEnd.isEmpty()) {
            errorMessages.add("Check Point Not Found");
            return responseData.failResponse(errorMessages, HttpStatus.NOT_FOUND.value());
        }

        Line data = modelMapper.map(dtoLine, Line.class);
        Line check = lineRepo.findByCode(data.getCode());
        if (check != null) {
            errorMessages.add("Data Conflict");
            return responseData.failResponse(errorMessages, HttpStatus.CONFLICT.value());
        }
        lineRepo.save(data);
        return responseData.successResponse(data, HttpStatus.CREATED.value());
    }

    @Override
    public Map getAll(int size, int page, String name) {
        Page<Line> list = null;
        Pageable show_data = PageRequest.of(page, size, Sort.by("id").ascending());
        if (name != null && !name.isEmpty()) {
            list = lineRepo.findByNameLike("%" + name + "%", show_data);
        } else {
            list = lineRepo.getAllData(show_data);
        }
        ResponseMetaData metaData = modelMapper.map(list, ResponseMetaData.class);

        return responseData.successResponseWithMeta(list.getContent(), metaData, HttpStatus.OK.value());
    }


    @Override
    public Map updateOne(Line line) {
        List<String> errorMessages = new ArrayList<>();
        Optional<Line> optionalCurrData = lineRepo.findById(line.getId());
        Line check = lineRepo.findByCode(line.getCode());
        Optional<CheckPoint> startcheckPoint = checkPointRepo.findById(line.getStartCheckpoint().getId());
        Optional<CheckPoint> endcheckPoint = checkPointRepo.findById(line.getEndCheckpoint().getId());
        Optional<CableType> cableType = cableTypeRepo.findById(line.getCableType().getId());

        if (optionalCurrData.isEmpty() || endcheckPoint.isEmpty() || startcheckPoint.isEmpty() || cableType.isEmpty()) {
            errorMessages.add("Data Not Found");
            return responseData.failResponse(errorMessages, HttpStatus.NOT_FOUND.value());
        }
        if (check != null && line.getId() != check.getId()) {
            errorMessages.add("Data Conflict");
            return responseData.failResponse(errorMessages, HttpStatus.CONFLICT.value());
        }

        line.setUpdatedDate(new Date());
        lineRepo.save(line);
        DTOLine viewNewData = modelMapper.map(line, DTOLine.class);
        return responseData.successResponse(viewNewData, HttpStatus.OK.value());
    }

    @Override
    public Map removeById(UUID id) {
        Optional<Line> optionalCurrData = lineRepo.findById(id);
        if (optionalCurrData.isEmpty()) {
            List<String> errorMessages = new ArrayList<>();
            errorMessages.add("Data Not Found");
            return responseData.failResponse(errorMessages, HttpStatus.NOT_FOUND.value());
        }

        Line line = modelMapper.map(optionalCurrData, Line.class);

        line.setDeletedDate(new Date());
        lineRepo.save(line);

        DTOLine viewNewData = modelMapper.map(line, DTOLine.class);
        return responseData.successResponse(viewNewData, HttpStatus.OK.value());
    }
}
