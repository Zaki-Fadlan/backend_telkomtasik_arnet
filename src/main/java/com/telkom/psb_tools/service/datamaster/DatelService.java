package com.telkom.psb_tools.service.datamaster;

import com.telkom.psb_tools.model.datamaster.Datel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;

public interface DatelService {
    public Map addDatel(Datel datel);

    public Map getAll(int size,int page);

    public Map getById(Long datel);

//    public Map findByName(String name, Integer page, Integer size);

    Page<Datel> finByNameLike(String name, Pageable pageable);

    public Map updateDatel(Datel datel);

    public Map deleteDatel(Long datel);
}
