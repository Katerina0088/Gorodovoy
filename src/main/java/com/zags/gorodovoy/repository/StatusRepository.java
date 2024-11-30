package com.zags.gorodovoy.repository;

import com.zags.gorodovoy.models.Status;
import com.zags.gorodovoy.models.TypeOfService;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface StatusRepository   extends CrudRepository<Status, Long> {
    @Query("SELECT u FROM Status u WHERE CONCAT(u.id, u.statusName) LIKE %?1%")
    public Iterable<Status> search(String keyword);

    List<Status> findAll();}