package com.zags.gorodovoy.repository;

import com.zags.gorodovoy.models.Task;
import com.zags.gorodovoy.models.TypeOfService;


import org.springframework.data.repository.CrudRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface TypeOfServiceRepository  extends CrudRepository<TypeOfService, Long> {
    @Query("SELECT u FROM TypeOfService u WHERE CONCAT(u.id, u.serviceType) LIKE %?1%")
    public Iterable<TypeOfService> search(String keyword);

    List<TypeOfService> findAll();

}
