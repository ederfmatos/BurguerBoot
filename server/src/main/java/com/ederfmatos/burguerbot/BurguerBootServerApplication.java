package com.ederfmatos.burguerbot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class BurguerBootServerApplication {


    public static void main(String[] args) {
        SpringApplication.run(BurguerBootServerApplication.class, args);
    }

}
