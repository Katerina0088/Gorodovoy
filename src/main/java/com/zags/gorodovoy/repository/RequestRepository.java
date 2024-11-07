package com.zags.gorodovoy.repository;


import com.zags.gorodovoy.models.Request;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RequestRepository extends CrudRepository<Request, Long> {
    @Query("SELECT u FROM Request u WHERE CONCAT(u.id, u.requestDate, u.employee, u.service, u.status, u.user) LIKE %?1%")
    public Iterable<Request> search(String keyword);

    List<Request> findAllById(Long userId);
}