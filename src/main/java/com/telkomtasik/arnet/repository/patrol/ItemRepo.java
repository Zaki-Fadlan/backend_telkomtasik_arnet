package com.telkomtasik.arnet.repository.patrol;

import com.telkomtasik.arnet.model.patrol.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface ItemRepo extends CrudRepository<Item, UUID> {
    @Query("select c from Item c where " +
            "lower(c.name) like lower(concat('%', :name, '%')) or " +
            "lower(c.code) like lower(concat('%', :name, '%'))")
    Page<Item> findByNameLike(String name, Pageable pageable);

    @Query("select c from Item c where lower(c.code) = lower(:code)")
    Item findByCode(String code);

    @Query("SELECT c from Item c")
    Page<Item> getAllData(Pageable pageable);
}
