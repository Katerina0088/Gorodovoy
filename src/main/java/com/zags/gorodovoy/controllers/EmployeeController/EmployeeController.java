package com.zags.gorodovoy.controllers.EmployeeController;

import com.zags.gorodovoy.Services.EmployeeService;
import com.zags.gorodovoy.Services.UserService;
import com.zags.gorodovoy.dtoModels.employeePrivilegeReq;
import com.zags.gorodovoy.models.Employee;
import com.zags.gorodovoy.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private UserService userService;


    @PostMapping("/add")
    public ResponseEntity<String> addEmployee( @RequestBody employeePrivilegeReq employeePrivilegeReq) {
        try {
            Employee employee = employeePrivilegeReq.getEmployee();

            employeeService.deleteByUserId(employee.getUserId());
            employeeService.add(employee);
            userService.saveUserPrivilegeRole( employee.getUserId(), employeePrivilegeReq.getPrivilegeRole());
            return ResponseEntity.ok("Сотрудник добавлен");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Ошибка при добавлении сотрудника: " + e.getMessage());
        }
    }

    @DeleteMapping("/deleted")
    public ResponseEntity<String> deleteEmployee( @RequestBody Long userId) {
        try{
            employeeService.deleteByUserId(userId);
            userService.saveUserPrivilegeRole(userId, "ROLE_USER");

            return ResponseEntity.status(HttpStatus.OK)
                    .body("Пользователь удален из сотрудников" );
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Ошибка при удалении сотрудника: " + e.getMessage());
        }
    }
}