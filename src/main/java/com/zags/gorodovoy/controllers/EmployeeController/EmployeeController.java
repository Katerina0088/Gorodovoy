package com.zags.gorodovoy.controllers.EmployeeController;

import com.zags.gorodovoy.Services.EmployeeService.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/add")
    public ResponseEntity<String> addEmployee(@RequestParam Long userId, @RequestParam Long roleId) {
        //employeeService.deleteByUserId(userId);
        //employeeService.add(employee);
        return ResponseEntity.ok("Сотрудник добавлен");
    }
}
