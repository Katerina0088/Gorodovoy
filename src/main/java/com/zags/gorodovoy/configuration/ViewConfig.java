package com.zags.gorodovoy.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ViewConfig {


    @Bean(name = "home")
    public String homeView() {
        return "home/home";
    }


    @Bean(name = "administrator")
    public String administratorView() {
        return "administrator/administrator";
    }
}