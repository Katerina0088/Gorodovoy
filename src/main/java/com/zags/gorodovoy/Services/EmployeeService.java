package com.zags.gorodovoy.Services;

import com.zags.gorodovoy.models.Employee;
import com.zags.gorodovoy.repository.EmployeeRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {  //вывод сотрудников из базы данных
    @Autowired
    private EmployeeRepository employeeRepository;

    public Iterable<Employee> listALl(String keyword) {
        if (keyword != null) {
            return employeeRepository.search(keyword); //Созданный метод в репозитории
        }
        return employeeRepository.findAll();
    }

    public void deleteByUserId(Long userId) {
        try {
            employeeRepository.deleteByUserId(userId);

        } catch (Exception e) {
            throw new RuntimeException("Failed to delete employee", e);
        }
    }

    public void add(Employee employee) {
        try {
            employeeRepository.save(employee);
        } catch (Exception e) {
            throw new RuntimeException("Failed to add employee", e);
        }
    }

    public List<Employee> getAll() {
        try {
            List<Employee> employees  = employeeRepository.findAllEmployees();

            return  employees;
        } catch (Exception e) {
            throw new RuntimeException("Failed to get All employee", e);
        }
    }
}