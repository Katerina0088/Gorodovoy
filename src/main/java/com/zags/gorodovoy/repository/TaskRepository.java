package com.zags.gorodovoy.repository;


import com.zags.gorodovoy.models.Task;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TaskRepository extends CrudRepository<Task, Long> {
    @Query("SELECT t FROM Task t WHERE CONCAT(t.id, t.requestDate, t.employee, t.service, t.status, t.user) LIKE %?1%")
    public Iterable<Task> search(String keyword);

    List<Task> findAllById(Long userId);
}