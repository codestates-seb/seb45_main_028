package com.mainproject.be28;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
class Be28Application {

    public static void main(String[] args) {
        SpringApplication.run(Be28Application.class, args);
    }

}
