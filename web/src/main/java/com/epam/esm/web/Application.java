package com.epam.esm.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EntityScan("com.epam.esm.core.entity")
@ComponentScan("com.epam.esm")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}