package com.gcu.taskmanager.service;

import com.gcu.taskmanager.models.Task;
import com.gcu.taskmanager.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    private static final Logger logger = LoggerFactory.getLogger(TaskService.class);

    @Autowired
    private TaskRepository taskRepository;

    public List<Task> getAllTasks() {
        logger.info("TaskService.getAllTasks() - Entering");
        List<Task> tasks = taskRepository.findAll();
        logger.info("TaskService.getAllTasks() - Exiting with {} tasks", tasks.size());
        return tasks;
        return taskRepository.findAll();
    }

    public Optional<Task> getTaskById(Long id) {
        logger.info("TaskService.getTaskById() - Entering with id: {}", id);
        if (task.isPresent()) {
            logger.info("TaskService.getTaskById() - Task found");
        } else {
            logger.warn("TaskService.getTaskById() - No task found with id: {}", id);
        }
        return task;
        return taskRepository.findById(id);
    }

    public Task createTask(Task task) {
        logger.info("TaskService.createTask() - Entering with task: {}", task);
        Task savedTask = taskRepository.save(task);
        logger.info("TaskService.createTask() - Task saved with id: {}", savedTask.getId());
        return savedTask;
        return taskRepository.save(task);
    }

    public Task updateTask(Long id, Task updatedTask) {
        logger.info("TaskService.updateTask() - Entering with id: {}", id);
        return taskRepository.findById(id).map(task -> {
            task.setTitle(updatedTask.getTitle());
            task.setDescription(updatedTask.getDescription());
            task.setDueDate(updatedTask.getDueDate());
            task.setStatus(updatedTask.getStatus());
            Task saved = taskRepository.save(task);
            logger.info("TaskService.updateTask() - Task updated: {}", saved);
            return saved;
        }).orElseGet(() -> {
            logger.warn("TaskService.updateTask() - No task found with id: {}", id);
            return null;
        });
            return taskRepository.save(task);
        }).orElse(null);
    }

    public void deleteTask(Long id) {
        logger.info("TaskService.deleteTask() - Deleting task with id: {}", id);
        taskRepository.deleteById(id);
        logger.info("TaskService.deleteTask() - Task deleted");
    }
}
