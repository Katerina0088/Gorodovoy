package com.zags.gorodovoy.controllers;

import com.zags.gorodovoy.Services.TaskService;
import com.zags.gorodovoy.Services.UserService;
import com.zags.gorodovoy.models.*;

import com.zags.gorodovoy.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller  //обработка переходов по сайту, за это отвечает контроллер
public class GorodovoyController {

    @Autowired // подключаем репозиторий для работы с базой по CRUD
    private EmployeeRepository employeeRepository;
    @Autowired // подключаем репозиторий для работы с базой по CRUD
    private DocumentRepository documentRepository;
    @Autowired // подключаем репозиторий для работы с базой по CRUD
    private TaskRepository taskRepository;
    @Autowired // подключаем репозиторий для работы с базой по CRUD
    private RoleRepository roleRepository;
    @Autowired // подключаем репозиторий для работы с базой по CRUD
    private TypeOfServiceRepository typeOfServiceRepository;
    @Autowired // подключаем репозиторий для работы с базой по CRUD
    private UserRepository userRepository;



    @Autowired // Подключение конфигурации для наших страниц, чтобы не прописывать путь до них в коде контроллера
    @Qualifier("home")
    private String home;


    @Autowired // Подключение конфигурации для наших страниц, чтобы не прописывать путь до них в коде контроллера
    @Qualifier("administrator")
    private String administrator;
    @Autowired // Подключение конфигурации для наших страниц, чтобы не прописывать путь до них в коде контроллера
    @Qualifier("tasks")
    private String task;
    @Autowired
    private UserService userService;
    @Autowired
    private TaskService taskService;

    // тут каждая функция обрабатывает определенный url ("/") значит начальная страница.
    // Т.е. при переходе на главную страницу вызывается эта функция
    @GetMapping({"/","/home"})
    public String home( Model model) { // Model model - обязательный параметр
        try {
            String userName = userService.getCurrentUserName();
            model.addAttribute("userName", userName );
            model.addAttribute("title", "Загс. Главная страница."); // что вернем на эту страницу в виде данных
            return home; // тут просто вызывается шаблон, который вернем
        }
        catch (Exception e) {
            model.addAttribute("errorMessage", "Ошибка при загрузке списка сотрудников: " + e.getMessage());
            return null;
        }
    }

    @GetMapping("/administrator")
    public String administrator(Model model) {
        try {
            List<Employee> employees  = employeeRepository.findAllEmployees();
            List<Role> roles = roleRepository.findAllRoles();
            List<User> users =  userRepository.findAll();
            String userName = userService.getCurrentUserName();

            model.addAttribute("userName", userName );
            model.addAttribute("employees", employees );
            model.addAttribute("roles", roles );
            model.addAttribute("users", users);
            return administrator;
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Ошибка при загрузке страницы administrator: " + e.getMessage());
            return null;
        }
    }

    @GetMapping("/tasks")
    public String tasks(Model model) {
        try {
            List<Task> tasks = taskRepository.findAllTasks();
            List<Employee> employees  = employeeRepository.findAllEmployees();
            List<Role> roles = roleRepository.findAllRoles();
            List<User> users =  userRepository.findAll();
            String userName = userService.getCurrentUserName();

            model.addAttribute("userName", userName );
            model.addAttribute("employees", employees );
            model.addAttribute("roles", roles );
            model.addAttribute("users", users);
            model.addAttribute("tasks", tasks);
            return task;
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Ошибка при загрузке страницы task: " + e.getMessage());
            return null;
        }
    }



}

