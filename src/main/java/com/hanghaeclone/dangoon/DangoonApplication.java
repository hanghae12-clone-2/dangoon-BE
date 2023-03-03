package com.hanghaeclone.dangoon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class DangoonApplication {

    public static void main(String[] args) {
        SpringApplication.run(DangoonApplication.class, args);
    }

}
