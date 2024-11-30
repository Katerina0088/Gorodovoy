package com.zags.gorodovoy.controllers.TaskController;

import com.zags.gorodovoy.Services.TaskService;
import com.zags.gorodovoy.Services.UserService;
import com.zags.gorodovoy.dtoModels.TaskFilter;
import com.zags.gorodovoy.models.Task;
import com.zags.gorodovoy.models.User;
import com.zags.gorodovoy.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;


@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;
    @Autowired
    private UserService userService;
    @Autowired
    private TaskRepository taskRepository;

     @GetMapping("/list")
    public ResponseEntity<List<Task>> getAllTasks() {
        try {
            User currentUser = userService.getCurrentUser();

            List<Task> tasks;
            TaskFilter taskFilter = new TaskFilter();
            taskFilter.setUserId(currentUser.getId());
            if("ROLE_USER".equals(currentUser.getRole()))
                tasks = taskService.getTasksByFilters(taskFilter);
            else
                tasks = taskRepository.findAllTasks();

            return ResponseEntity.ok(tasks);
        } catch (Exception e) {
            return null;
        }
    }




    @PostMapping("/add")
    public ResponseEntity<String> addTask( @RequestBody Task task) {
        try {
            task.setStatusId(task.getStatusId() != null? task.getStatusId() : 1);
            Date currentDate = new Date();

            task.setDateCreate(currentDate);
            task.setDateEdit(currentDate);
            task.setDateStart(currentDate);


            taskService.createTask(task);

            return ResponseEntity.ok("Задача создана");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Ошибка создания задачи: " + e.getMessage());
        }
    }

    @PostMapping("/update")
    public ResponseEntity<String> updateTask( @RequestBody Task task) {
        try {
            Task taskById = taskService.getById(task.getId());
            Date currentDate = new Date();

            taskById.setDateEdit(currentDate);

            taskById.setStatusId(task.getStatusId() != null? task.getStatusId() : taskById.getStatusId());
            taskById.setEmployeeId(task.getEmployeeId()!= null? task.getEmployeeId() : taskById.getEmployeeId());
            taskById.setServiceId(task.getServiceId()!= null? task.getServiceId() : taskById.getServiceId());

            if(taskById.getStatusId() == 3)
                taskById.setDateFinish(currentDate);
            else
                taskById.setDateFinish(null);

            taskService.updateTask(taskById);

            return ResponseEntity.ok("Задача обновлена");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Ошибка обновленении задачи: " + e.getMessage());
        }
    }

    @PostMapping("/filters") // This should match the endpoint you are calling
    public ResponseEntity<List<Task>> getTasksFilters(@RequestBody TaskFilter taskFilter) {
        try {
            User currentUser = userService.getCurrentUser();
            if ("ROLE_USER".equals(currentUser.getRole()) && taskFilter.getUserId() == null) {
                taskFilter.setUserId(currentUser.getId());
            }
            List<Task> result = taskService.getTasksByFilters(taskFilter);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            System.err.println("Ошибка получения задач по фильтру: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @DeleteMapping("/deleted")
    public ResponseEntity<String> deleteTask( @RequestBody Long taskId) {
        try{
            taskService.deleteTask(taskId);

            return ResponseEntity.status(HttpStatus.OK)
                    .body("Заявка удалена" );
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Ошибка при удалении заявки: " + e.getMessage());
        }
    }
}