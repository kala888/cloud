package com.method51.manage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;
import de.codecentric.boot.admin.config.EnableAdminServer;

@SpringBootApplication
@EnableConfigServer
@EnableAdminServer
public class ManageApplication {

	public static void main(String[] args) {
		SpringApplication.run(ManageApplication.class, args);
	}
}
