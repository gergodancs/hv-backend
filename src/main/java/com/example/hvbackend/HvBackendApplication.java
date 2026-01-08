package com.example.hvbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
public class HvBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(HvBackendApplication.class, args);
    }

}
