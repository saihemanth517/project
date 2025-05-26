package com.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan("com.demo.model")
public class TravelPlannerBackend1Application {

    public static void main(String[] args) {
        SpringApplication.run(TravelPlannerBackend1Application.class, args);
    }
}
