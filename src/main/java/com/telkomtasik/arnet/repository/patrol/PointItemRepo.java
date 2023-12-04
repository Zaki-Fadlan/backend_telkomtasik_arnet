package com.telkomtasik.arnet.repository.patrol;

import com.telkomtasik.arnet.model.patrol.PointItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface PointItemRepo extends CrudRepository<PointItem, UUID> {
//    @Query("select c from PointItem c where " +
//            "lower(c.name) like lower(concat('%', :name, '%')) or " +
//            "lower(c.latitude) like lower(concat('%', :name, '%')) or " +
//            "lower(c.longitude) like lower(concat('%', :name, '%')) or " +
//            "lower(c.code) like lower(concat('%', :name, '%'))")
//    Page<Point> findByNameLike(String name, Pageable pageable);

//    @Query("select c from PointItem c where lower(c.code) = lower(:code)")
//    Point findByCode(String code);

    @Query("SELECT c from PointItem c")
    Page<PointItem> getAllData(Pageable pageable);
}
