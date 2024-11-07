package com.zags.gorodovoy;
//Это основной класс для запуска приложения

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GorodovoyApplication {

    public static void main(String[] args) {
        SpringApplication.run(GorodovoyApplication.class, args);
    }

}
