package com.telkomtasik.arnet.repository.patrol;

import com.telkomtasik.arnet.model.patrol.CableType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface CableTypeRepo extends CrudRepository<CableType, Long> {
    @Query("select c from CableType c where lower(c.name) like lower(concat('%', :name, '%'))")
    Page<CableType> findByNameLike(String name, Pageable pageable);

    @Query("select c from CableType c where lower(c.code) = lower(:code)")
    CableType findByCode(String code);

    @Query("SELECT c from CableType c")
    Page<CableType> getAllData(Pageable pageable);
}
