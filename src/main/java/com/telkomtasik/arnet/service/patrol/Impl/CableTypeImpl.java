package com.telkomtasik.arnet.service.patrol.Impl;

import com.telkomtasik.arnet.dto.ResponseData;
import com.telkomtasik.arnet.dto.ResponseMetaData;
import com.telkomtasik.arnet.dto.patrol.DTOCableType;
import com.telkomtasik.arnet.model.patrol.CableType;
import com.telkomtasik.arnet.repository.patrol.CableTypeRepo;
import com.telkomtasik.arnet.service.patrol.CableTypeService;
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
public class CableTypeImpl implements CableTypeService {
    @Autowired
    private CableTypeRepo cableTypeRepo;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ResponseData responseData;

    @Override
    public Map createOne(DTOCableType dtoCableType) {
        CableType data = modelMapper.map(dtoCableType, CableType.class);
        CableType check = cableTypeRepo.findByCode(data.getCode());
        if (check == null) {
            cableTypeRepo.save(data);
            return responseData.successResponse(dtoCableType, HttpStatus.CREATED.value());
        } else {
            List<String> errorMessages = new ArrayList<>();
            errorMessages.add("Data Conflict");
            return responseData.failResponse(errorMessages, HttpStatus.CONFLICT.value());
        }
    }

    @Override
    public Map getAll(int size, int page, String name) {
        Page<CableType> list = null;
        Pageable show_data = PageRequest.of(page, size, Sort.by("id").ascending());
        if (name != null && !name.isEmpty()) {
            list = cableTypeRepo.findByNameLike("%" + name + "%", show_data);
        } else {
            list = cableTypeRepo.getAllData(show_data);
        }
//        List<DTOCableType> cableTypeDTOs = StreamSupport.stream(list.getContent().spliterator(), true)
//                .map(cableType -> modelMapper.map(cableType, DTOCableType.class))
//                .collect(Collectors.toList());
        ResponseMetaData metaData = modelMapper.map(list, ResponseMetaData.class);

        return responseData.successResponseWithMeta(list.getContent(), metaData, HttpStatus.OK.value());
    }


    @Override
    public Map updateOne(CableType cableType) {
        List<String> errorMessages = new ArrayList<>();
        Optional<CableType> optionalCurrData = cableTypeRepo.findById(cableType.getId());
        CableType check = cableTypeRepo.findByCode(cableType.getCode());

        if (optionalCurrData.isEmpty()) {
            errorMessages.add("Data Not Found");
            return responseData.failResponse(errorMessages, HttpStatus.NOT_FOUND.value());
        }
        if (check != null && cableType.getId() != check.getId()) {
            errorMessages.add("Data Conflict");
            return responseData.failResponse(errorMessages, HttpStatus.CONFLICT.value());
        }
        
        cableType.setUpdatedDate(new Date());
        cableTypeRepo.save(cableType);
        DTOCableType viewNewData = modelMapper.map(cableType, DTOCableType.class);
        return responseData.successResponse(viewNewData, HttpStatus.OK.value());
    }

    @Override
    public Map removeById(Long id) {
        Optional<CableType> optionalCurrData = cableTypeRepo.findById(id);
        if (optionalCurrData.isEmpty()) {
            List<String> errorMessages = new ArrayList<>();
            errorMessages.add("Data Not Found");
            return responseData.failResponse(errorMessages, HttpStatus.NOT_FOUND.value());
        }

        CableType cableType = modelMapper.map(optionalCurrData, CableType.class);

        cableType.setDeletedDate(new Date());
        cableTypeRepo.save(cableType);

        DTOCableType viewNewData = modelMapper.map(cableType, DTOCableType.class);
        return responseData.successResponse(cableType, HttpStatus.OK.value());
    }

}
