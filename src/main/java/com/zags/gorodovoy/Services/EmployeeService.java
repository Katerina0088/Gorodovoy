package com.zags.gorodovoy.Services;

import com.zags.gorodovoy.models.Employee;
import com.zags.gorodovoy.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {  //вывод сотрудников из базы данных
    @Autowired
    private EmployeeRepository repo;

    public Iterable<Employee> listALl(String keyword) {
        if (keyword != null) {
            return repo.search(keyword); //Созданный метод в репозитории
        }
        return repo.findAll();
    }
}