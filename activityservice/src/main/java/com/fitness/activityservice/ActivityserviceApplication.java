package com.fitness.activityservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@EnableMongoAuditing
@SpringBootApplication
public class ActivityserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ActivityserviceApplication.class, args);
    }
}
