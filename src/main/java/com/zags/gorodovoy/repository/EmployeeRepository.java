package com.zags.gorodovoy.repository;

import com.zags.gorodovoy.models.Employee;
import com.zags.gorodovoy.models.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @Transactional
    @Modifying
    @Query("DELETE FROM Employee e WHERE e.userId = :userId")
    int deleteByUserId(@Param("userId") Long userId);

    @Query("SELECT DISTINCT e FROM Employee e LEFT JOIN FETCH e.user u LEFT JOIN FETCH e.role r")
    List<Employee> findAllEmployees();

    @Query("SELECT e FROM Employee e WHERE CONCAT(e.id, e.user, e.role) LIKE %?1%")
    public Iterable<Employee> search(String keyword);

}