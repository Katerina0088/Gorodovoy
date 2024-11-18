package com.zags.gorodovoy.controllers;


import com.zags.gorodovoy.Services.UserService;
import com.zags.gorodovoy.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

@Controller
@RequestMapping("/register")
public class RegistrationController {
    private UserService userService;

    @Autowired
    public RegistrationController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping
    public String showRegPage(Model model) {

        model.addAttribute("headerHidde", true );
        return "register";
    }

    @PostMapping
    public String registerUser(
            @RequestParam(required = true) String username,
            @RequestParam(required = true) String password,
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName,
            @RequestParam(required = false) String middleName,
            @RequestParam(required = false) String birthDate,
            @RequestParam(required = false) String passportSeries,
            @RequestParam(required = false) String passportNumber
    ) {
        // Проверяем, что пользователь хочет зарегистрироваться
        if (username.equals("!@") && password.equals("!@")) {
            return "redirect:/login";
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);

        // Если предоставлены дополнительные данные, заполняем объект User
        if (firstName != null && !firstName.isEmpty()) {
            user.setFirstName(firstName);
        }
        if (lastName != null && !lastName.isEmpty()) {
            user.setLastName(lastName);
        }
        if (middleName != null && !middleName.isEmpty()) {
            user.setMiddleName(middleName);
        }
        if (birthDate != null) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date parsedDate = sdf.parse(birthDate);
                user.setBirthDate(parsedDate);
            } catch (ParseException e) {
                System.err.println("Ошибка при парсинге даты рождения: " + e.getMessage());
                user.setBirthDate(null);
            }
        }
        if (passportSeries != null && !passportSeries.isEmpty()) {
            user.setPassportSeries(passportSeries);
        }
        if (passportNumber != null && !passportNumber.isEmpty()) {
            user.setPassportNumber(passportNumber);
        }

        // Сохраняем пользователя
        if (userService.saveUser(user)) {
            return "redirect:/login";
        } else {
            return "redirect:/register";
        }
    }}
