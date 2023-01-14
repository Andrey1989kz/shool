package ru.hogwarts.shool;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition

public class ShoolApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShoolApplication.class, args);
    }

}
