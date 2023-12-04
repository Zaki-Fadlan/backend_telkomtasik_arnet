package com.telkomtasik.arnet.service.patrol.Impl;

import com.telkomtasik.arnet.dto.ResponseData;
import com.telkomtasik.arnet.dto.ResponseMetaData;
import com.telkomtasik.arnet.dto.patrol.DTOItem;
import com.telkomtasik.arnet.model.patrol.Item;
import com.telkomtasik.arnet.repository.patrol.ItemRepo;
import com.telkomtasik.arnet.service.patrol.ItemService;
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
public class ItemImpl implements ItemService {
    @Autowired
    private ItemRepo itemRepo;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ResponseData responseData;

    @Override
    public Map createOne(DTOItem dtoItem) {
        Item data = modelMapper.map(dtoItem, Item.class);
        Item check = itemRepo.findByCode(data.getCode());
        if (check == null) {
            itemRepo.save(data);
            return responseData.successResponse(dtoItem, HttpStatus.CREATED.value());
        } else {
            List<String> errorMessages = new ArrayList<>();
            errorMessages.add("Data Conflict");
            return responseData.failResponse(errorMessages, HttpStatus.CONFLICT.value());
        }
    }

    @Override
    public Map getAll(int size, int page, String name) {
        Page<Item> list = null;
        Pageable show_data = PageRequest.of(page, size, Sort.by("id").ascending());
        if (name != null && !name.isEmpty()) {
            list = itemRepo.findByNameLike("%" + name + "%", show_data);
        } else {
            list = itemRepo.getAllData(show_data);
        }
        ResponseMetaData metaData = modelMapper.map(list, ResponseMetaData.class);

        return responseData.successResponseWithMeta(list.getContent(), metaData, HttpStatus.OK.value());
    }

    @Override
    public Map updateOne(Item item) {
        List<String> errorMessages = new ArrayList<>();
        Optional<Item> optionalCurrData = itemRepo.findById(item.getId());
        Item check = itemRepo.findByCode(item.getCode());

        if (optionalCurrData.isEmpty()) {
            errorMessages.add("Data Not Found");
            return responseData.failResponse(errorMessages, HttpStatus.NOT_FOUND.value());
        }
        if (check != null && item.getId() != check.getId()) {
            errorMessages.add("Data Conflict");
            return responseData.failResponse(errorMessages, HttpStatus.CONFLICT.value());
        }

        item.setUpdatedDate(new Date());
        itemRepo.save(item);
        DTOItem viewNewData = modelMapper.map(item, DTOItem.class);
        return responseData.successResponse(viewNewData, HttpStatus.OK.value());
    }

    @Override
    public Map removeById(UUID id) {
        Optional<Item> optionalCurrData = itemRepo.findById(id);

        if (optionalCurrData.isEmpty()) {
            List<String> errorMessages = new ArrayList<>();
            errorMessages.add("Data Not Found");
            return responseData.failResponse(errorMessages, HttpStatus.NOT_FOUND.value());
        }

        Item items = modelMapper.map(optionalCurrData, Item.class);

        items.setDeletedDate(new Date());
        itemRepo.save(items);

        DTOItem viewNewData = modelMapper.map(items, DTOItem.class);
        return responseData.successResponse(viewNewData, HttpStatus.OK.value());
    }
}
