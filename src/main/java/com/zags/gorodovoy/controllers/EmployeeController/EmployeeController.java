package com.zags.gorodovoy.controllers.EmployeeController;

import com.zags.gorodovoy.Services.EmployeeService;
import com.zags.gorodovoy.models.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/add")
    public ResponseEntity<String> addEmployee(@RequestBody Employee employee) {
        try {
            employeeService.deleteByUserId(employee.getUserId());
            employeeService.add(employee);
            return ResponseEntity.ok("Сотрудник добавлен");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Ошибка при добавлении сотрудника: " + e.getMessage());
        }
    }
}