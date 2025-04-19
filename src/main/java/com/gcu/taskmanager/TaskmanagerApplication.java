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
<<<<<<< HEAD
	    logger.info("TaskmanagerApplication.main() - Application starting");
        SpringApplication.run(TaskmanagerApplication.class, args);
        logger.info("TaskmanagerApplication.main() - Application started successfully");
=======
		SpringApplication.run(TaskmanagerApplication.class, args);
>>>>>>> parent of f36aa8c (Added basic logging)
	}

}