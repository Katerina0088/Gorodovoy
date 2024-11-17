package com.zags.gorodovoy.controllers;

import com.zags.gorodovoy.Services.EmployeeService.UserService;
import com.zags.gorodovoy.dto.UserDto;
import com.zags.gorodovoy.models.*;

import com.zags.gorodovoy.repository.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.repository.query.Param;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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



    @Autowired // Подключение конфигурации для наших страниц, чтобы не прописывать путь до них в коде контроллера
    @Qualifier("home")
    private String home;




    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    public GorodovoyController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model){
        UserDto user = new UserDto();
        model.addAttribute("user", user);
        return "register";
    }
    @PostMapping("/register/save")
    public String registration(@Valid @ModelAttribute("user") UserDto userDto,
                               BindingResult result,
                               Model model){
        User existingUser = userService.findUserByEmail(userDto.getEmail());

        if(existingUser != null && existingUser.getEmail() != null && !existingUser.getEmail().isEmpty()){
            result.rejectValue("email", null,
                    "There is already an account registered with the same email");
        }

        if(result.hasErrors()){
            model.addAttribute("user", userDto);
            return "/register";
        }

        userService.saveUser(userDto);
        return "redirect:/register?success";
    }



    // тут каждая функция обрабатывает определенный url ("/") значит начальная страница.
    // Т.е. при переходе на главную страницу вызывается эта функция
    @GetMapping("/home")
    public String home( Model model) { // Model model - обязательный параметр
        try {
            model.addAttribute("title", "Загс. Главная страница."); // что вернем на эту страницу в виде данных
            return home; // тут просто вызывается шаблон, который вернем
        }
        catch (Exception e) {
            model.addAttribute("errorMessage", "Ошибка при загрузке списка сотрудников: " + e.getMessage());
            return null;
        }
    }
    @RequestMapping("/")
    public String viewHomePage(Model model, @Param("keyword") String keyword) {
        try {
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

            model.addAttribute("emplyees", employees );
            model.addAttribute("roles", roles );
            model.addAttribute("users", users);
            return "admin";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Ошибка при загрузке списка сотрудников: " + e.getMessage());
            return null;
        }
    }
}
    /*@GetMapping("/myrequest/{id}")
    public String viewMyRequests(@PathVariable Long userId, Model model) {
        List<Task> tasks = TaskRepository.findAllById(userId);
        model.addAttribute("requests", tasks);
        return "my-requests";
    }}*/

