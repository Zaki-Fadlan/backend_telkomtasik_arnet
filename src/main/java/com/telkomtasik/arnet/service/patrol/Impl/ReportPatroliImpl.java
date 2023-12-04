package com.telkomtasik.arnet.service.patrol.Impl;

import com.telkomtasik.arnet.dto.ResponseData;
import com.telkomtasik.arnet.dto.ResponseMetaData;
import com.telkomtasik.arnet.dto.patrol.DTOReportPatroli;
import com.telkomtasik.arnet.dto.patrol.DTOSchedulePatroli;
import com.telkomtasik.arnet.model.patrol.ReportPatroli;
import com.telkomtasik.arnet.model.patrol.SchedulePatroli;
import com.telkomtasik.arnet.repository.patrol.*;
import com.telkomtasik.arnet.service.patrol.ReportPatroliService;
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
public class ReportPatroliImpl implements ReportPatroliService {
    @Autowired
    private SchedulePatroliRepo schedulePatroliRepo;
    @Autowired
    private ReportPatroliRepo reportPatroliRepo;
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
    public Map createOne(DTOReportPatroli dtoReportPatroli) {
        ReportPatroli data = modelMapper.map(dtoReportPatroli, ReportPatroli.class);
        reportPatroliRepo.save(data);
        return responseData.successResponse(dtoReportPatroli, HttpStatus.CREATED.value());

    }

    @Override
    public Map getAll(int size, int page, String name) {
        Page<ReportPatroli> list = null;
        Pageable show_data = PageRequest.of(page, size, Sort.by("id").ascending());
        list = reportPatroliRepo.getAllData(show_data);
        ResponseMetaData metaData = modelMapper.map(list, ResponseMetaData.class);
        return responseData.successResponseWithMeta(list.getContent(), metaData, HttpStatus.OK.value());
    }

    @Override
    public Map updateOne(ReportPatroli reportPatroli) {

        Optional<ReportPatroli> optionalCurrData = reportPatroliRepo.findById(reportPatroli.getId());
        if (optionalCurrData.isEmpty()) {
            List<String> errorMessages = new ArrayList<>();
            errorMessages.add("Data Not Found");
            return responseData.failResponse(errorMessages, HttpStatus.NOT_FOUND.value());
        }

        reportPatroli.setUpdatedDate(new Date());
        reportPatroliRepo.save(reportPatroli);
        DTOSchedulePatroli viewNewData = modelMapper.map(reportPatroli, DTOSchedulePatroli.class);
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
