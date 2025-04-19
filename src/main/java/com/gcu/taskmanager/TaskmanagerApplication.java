package com.gcu.taskmanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TaskmanagerApplication {

	public static void main(String[] args) {
	    String port = System.getenv("PORT");
	    if (port != null) {
	        System.getProperties().put("server.port", port);
	    }
        SpringApplication.run(TaskmanagerApplication.class, args);
	}

}