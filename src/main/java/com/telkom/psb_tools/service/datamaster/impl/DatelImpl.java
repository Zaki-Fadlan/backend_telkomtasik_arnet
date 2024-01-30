package com.telkom.psb_tools.service.datamaster.impl;

import com.telkom.psb_tools.model.datamaster.Datel;
import com.telkom.psb_tools.repository.datamaster.DatelRepository;
import com.telkom.psb_tools.service.datamaster.DatelService;
import com.telkom.psb_tools.utils.TemplateResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class DatelImpl implements DatelService {
    @Autowired
    public DatelRepository datelRepository;

    @Autowired
    public TemplateResponse templateResponse;

    @Override
    public Map addDatel(Datel datel) {
        try{
            Datel objDatel = datelRepository.save(datel);
            return templateResponse.successTemplate(objDatel);
        }catch (Exception e){
            return templateResponse.errorTemplate(e);
        }
    }
    @Override
    public Map getAll(int size, int page) {
        return null;
    }
    @Override
    public Map getById(Long datel) {
        return null;
    }
    @Override
    public Page<Datel> finByNameLike(String name, Pageable pageable) {
        return null;
    }
    @Override
    public Map updateDatel(Datel datel) {
        return null;
    }
    @Override
    public Map deleteDatel(Long datel) {
        return null;
    }
}
