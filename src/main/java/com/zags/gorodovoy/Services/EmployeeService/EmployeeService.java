package com.zags.gorodovoy.Services.EmployeeService;

import com.zags.gorodovoy.models.Employee;
import com.zags.gorodovoy.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        employeeRepository.deleteByUserId(userId);
    }

    public void add(Employee employee) {
        employeeRepository.save(employee);
    }

}