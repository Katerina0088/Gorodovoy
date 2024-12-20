package com.zags.gorodovoy.repository;


import com.zags.gorodovoy.models.Task;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TaskRepository extends CrudRepository<Task, Long> {
    @Query("SELECT t FROM Task t WHERE CONCAT(t.id, t.requestDate, t.employee, t.service, t.status, t.user) LIKE %?1%")
    public Iterable<Task> search(String keyword);

    @Query("SELECT t FROM Task t JOIN FETCH t.status s JOIN FETCH t.service se JOIN FETCH t.user us ORDER BY t.dateCreate DESC")
    List<Task> findAllTasks();

    List<Task> findAllById(Long userId);

    List<Task> findAll(Specification<Task> specification, Sort dateCreate);

    @Query("SELECT COUNT( t.status) FROM Task t WHERE t.status.id = 3")
    Integer countReadyTasks();

}