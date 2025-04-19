package com.gcu.taskmanager.controller;

import com.gcu.taskmanager.models.Task;
import com.gcu.taskmanager.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tasks")
public class TaskApiController {

    private static final Logger logger = LoggerFactory.getLogger(TaskApiController.class);

    @Autowired
    private TaskService taskService;

    @GetMapping
    public List<Task> getAllTasks() {
        logger.info("TaskApiController.getAllTasks() - Entering");
        List<Task> tasks = taskService.getAllTasks();
        logger.info("TaskApiController.getAllTasks() - Returning {} tasks", tasks.size());
        return tasks;
    }

    @GetMapping("/{id}")
    public Optional<Task> getTaskById(@PathVariable Long id) {
        logger.info("TaskApiController.getTaskById() - Entering with id: {}", id);
        return taskService.getTaskById(id);
    }

    @PostMapping
    public Task createTask(@RequestBody Task task) {
        logger.info("TaskApiController.createTask() - Entering with task: {}", task);
        return taskService.createTask(task);
    }

    @PutMapping("/{id}")
    public Task updateTask(@PathVariable Long id, @RequestBody Task task) {
        logger.info("TaskApiController.updateTask() - Entering with id: {}", id);
        return taskService.updateTask(id, task);
    }

    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable Long id) {
        logger.info("TaskApiController.deleteTask() - Entering with id: {}", id);
        taskService.deleteTask(id);
    }
}
