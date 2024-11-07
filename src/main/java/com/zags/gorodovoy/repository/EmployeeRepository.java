package com.zags.gorodovoy.repository;

import com.zags.gorodovoy.models.Employee;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.jpa.repository.Query;


public interface EmployeeRepository extends CrudRepository<Employee, Long> {
    @Query("SELECT e FROM Employee e WHERE CONCAT(e.id, e.firstName, e.middleName, e.lastName, e.birthDate, e.passportData) LIKE %?1%")
    public Iterable<Employee> search(String keyword);

}
