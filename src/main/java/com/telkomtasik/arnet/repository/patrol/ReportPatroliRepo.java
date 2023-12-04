package com.telkomtasik.arnet.repository.patrol;

import com.telkomtasik.arnet.model.patrol.ReportPatroli;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface ReportPatroliRepo extends CrudRepository<ReportPatroli, UUID> {

//    @Query("select c from ReportPatroli c where " +
//            "lower(c.code) like lower(concat('%', :name, '%')) or " +
//            "lower(c.name) like lower(concat('%', :name, '%'))")
//    Page<Line> findByNameLike(String name, Pageable pageable);

//    @Query("select c from ReportPatroli c where lower(c.code) = lower(:code)")
//    ReportPatroli findByCode(String code);

    @Query("SELECT c from ReportPatroli c")
    Page<ReportPatroli> getAllData(Pageable pageable);
}
