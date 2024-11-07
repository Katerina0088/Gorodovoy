package com.zags.gorodovoy.controllers;



import com.zags.gorodovoy.Services.EmployeeService;
import com.zags.gorodovoy.models.Employee;
import com.zags.gorodovoy.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller  //обработка переходов по сайту, за это отвечает контроллер
public class ExampleController {
    @Autowired // подключаем репозиторий для работы с базой по CRUD
    private EmployeeRepository employeeRepository;
    @Autowired
    private EmployeeService employeeService;

    // тут каждая функция обрабатывает определенный url ("/") значит начальная страница.
    // Т.е. при переходе на главную страницу вызывается эта функция
    @GetMapping("/example")
    public String example( Model model) { // Model model - обязательный параметр
        Iterable<Employee> employees = employeeRepository.findAll();
        model.addAttribute("employees", employees);
        model.addAttribute("title", "Загс. Главная страница."); // что вернем на эту страницу в виде данных
        return "example"; // тут просто вызывается шаблон, который вернем
    }
    @RequestMapping("/index")
    public String index( Model model, @Param("keyword") String keyword) { // Model model - обязательный параметр
        Iterable<Employee> employees = employeeService.listALl(keyword);
        model.addAttribute("employees", employees);
        model.addAttribute("keyword", keyword); // что вернем на эту страницу в виде данных
        return "example"; // тут просто вызывается шаблон, который вернем

}

}