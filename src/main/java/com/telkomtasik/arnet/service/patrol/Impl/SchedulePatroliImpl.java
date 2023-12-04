package com.telkomtasik.arnet.service.patrol.Impl;

import com.telkomtasik.arnet.dto.ResponseData;
import com.telkomtasik.arnet.dto.ResponseMetaData;
import com.telkomtasik.arnet.dto.patrol.DTOSchedulePatroli;
import com.telkomtasik.arnet.model.patrol.PointItem;
import com.telkomtasik.arnet.model.patrol.SchedulePatroli;
import com.telkomtasik.arnet.repository.patrol.ItemRepo;
import com.telkomtasik.arnet.repository.patrol.PointItemRepo;
import com.telkomtasik.arnet.repository.patrol.PointRepo;
import com.telkomtasik.arnet.repository.patrol.SchedulePatroliRepo;
import com.telkomtasik.arnet.service.patrol.SchedulePatroliService;
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
public class SchedulePatroliImpl implements SchedulePatroliService {
    @Autowired
    private SchedulePatroliRepo schedulePatroliRepo;
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
    public Map createOne(DTOSchedulePatroli dtoSchedulePatroli) {
        SchedulePatroli data = modelMapper.map(dtoSchedulePatroli, SchedulePatroli.class);
        schedulePatroliRepo.save(data);
        return responseData.successResponse(dtoSchedulePatroli, HttpStatus.CREATED.value());

    }

    @Override
    public Map getAll(int size, int page, String name) {
        Page<SchedulePatroli> list = null;
        Pageable show_data = PageRequest.of(page, size, Sort.by("id").ascending());
        list = schedulePatroliRepo.getAllData(show_data);
        ResponseMetaData metaData = modelMapper.map(list, ResponseMetaData.class);
        return responseData.successResponseWithMeta(list.getContent(), metaData, HttpStatus.OK.value());
    }

    @Override
    public Map updateOne(SchedulePatroli schedulePatroli) {

        Optional<SchedulePatroli> optionalCurrData = schedulePatroliRepo.findById(schedulePatroli.getId());
        Optional<PointItem> pointItem = pointItemRepo.findById(schedulePatroli.getPointItem().getId());
        if (optionalCurrData.isEmpty()) {
            List<String> errorMessages = new ArrayList<>();
            errorMessages.add("Data Not Found");
            return responseData.failResponse(errorMessages, HttpStatus.NOT_FOUND.value());
        }

        schedulePatroli.setUpdatedDate(new Date());
        schedulePatroliRepo.save(schedulePatroli);
        DTOSchedulePatroli viewNewData = modelMapper.map(schedulePatroli, DTOSchedulePatroli.class);
        return responseData.successResponse(viewNewData, HttpStatus.OK.value());
    }

    @Override
    public Map removeById(UUID id) {
        Optional<SchedulePatroli> optionalCurrData = schedulePatroliRepo.findById(id);

        if (optionalCurrData.isEmpty()) {
            List<String> errorMessages = new ArrayList<>();
            errorMessages.add("Data Not Found");
            return responseData.failResponse(errorMessages, HttpStatus.NOT_FOUND.value());
        }

        SchedulePatroli point = modelMapper.map(optionalCurrData, SchedulePatroli.class);

        point.setDeletedDate(new Date());
        schedulePatroliRepo.save(point);

        DTOSchedulePatroli viewNewData = modelMapper.map(point, DTOSchedulePatroli.class);
        return responseData.successResponse(viewNewData, HttpStatus.OK.value());
    }
}
