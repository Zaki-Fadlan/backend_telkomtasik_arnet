package com.telkomtasik.arnet.repository.patrol;

import com.telkomtasik.arnet.model.patrol.CheckPoint;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface CheckPointRepo extends CrudRepository<CheckPoint, UUID> {
    @Query("select c from CheckPoint c where " +
            "lower(c.name) like lower(concat('%', :name, '%')) or " +
            "lower(c.code) like lower(concat('%', :name, '%')) or " +
            "lower(c.latitude) like lower(concat('%', :name, '%')) or " +
            "lower(c.longitude) like lower(concat('%', :name, '%'))")
    Page<CheckPoint> findByNameLike(String name, Pageable pageable);

    @Query("select c from CheckPoint c where lower(c.code) = lower(:code)")
    CheckPoint findByCode(String code);

    @Query("SELECT c from CheckPoint c")
    Page<CheckPoint> getAllData(Pageable pageable);
}
