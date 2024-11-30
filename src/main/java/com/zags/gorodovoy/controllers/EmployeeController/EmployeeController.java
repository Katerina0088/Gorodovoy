package com.zags.gorodovoy.controllers.EmployeeController;

import com.zags.gorodovoy.Services.EmployeeService;
import com.zags.gorodovoy.Services.TaskService;
import com.zags.gorodovoy.Services.UserService;
import com.zags.gorodovoy.dtoModels.TaskFilter;
import com.zags.gorodovoy.dtoModels.employeePrivilegeReq;
import com.zags.gorodovoy.models.Employee;
import com.zags.gorodovoy.models.Task;
import com.zags.gorodovoy.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private UserService userService;
    @Autowired
    private TaskService taskService;

    @GetMapping("/list")
    public ResponseEntity<List<Employee>> getAllEmployees() {
        try {
            List<Employee> employeesList = employeeService.getAll();
            return ResponseEntity.ok(employeesList);
        } catch (Exception e) {
            return null;
        }
    }

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
            Long emploueeId = employeeService.getEmployeeByUserId(userId);
            TaskFilter taskFilter = new TaskFilter();
            taskFilter.setEmployeeId(emploueeId);
            List<Task>tasks = taskService.getTasksByFilters(taskFilter);

            for (Task task : tasks) {
                task.setEmployeeId(null);

                taskService.updateTask(task);

            }


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