package com.telkom.psb_tools.repository.datamaster;

import com.telkom.psb_tools.model.datamaster.Datel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DatelRepository extends PagingAndSortingRepository<Datel, Long>, CrudRepository<Datel, Long> {
    @Query("SELECT c from Datel c")
    Page<Datel> getAllData(Pageable pageable);
}
