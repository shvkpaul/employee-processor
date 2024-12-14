package com.shvkpaul.employee;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;

@SpringBootApplication
@EnableRetry
public class EmployeeProcessorApplication {

    public static void main(String[] args) {
        SpringApplication.run(EmployeeProcessorApplication.class, args);
    }

}
