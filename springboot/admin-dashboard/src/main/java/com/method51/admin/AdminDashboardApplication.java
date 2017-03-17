package com.method51.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import de.codecentric.boot.admin.config.EnableAdminServer;

@SpringBootApplication
@EnableAdminServer
public class AdminDashboardApplication {
    public static void main(String[] args) {
        SpringApplication.run(AdminDashboardApplication.class, args);
    }
}