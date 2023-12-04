package com.telkomtasik.arnet.repository;

import com.telkomtasik.arnet.model.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface StatusRepo extends CrudRepository<Status, Long> {
    @Query("select c from Status c where lower(c.name) like lower(concat('%', :name, '%'))")
    Page<Status> findByNameLike(String name, Pageable pageable);

    @Query("SELECT c from Status c")
    Page<Status> getAllData(Pageable pageable);
}
