package com.gcu.taskmanager.service;

import com.gcu.taskmanager.models.Task;
import com.gcu.taskmanager.repository.TaskRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;
    private static final Logger logger = LoggerFactory.getLogger(TaskService.class);

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public Optional<Task> getTaskById(Long id) {
        return taskRepository.findById(id);
    }

    public Task createTask(Task task) {
    	logger.info("Creating task: {}", task);        
        return taskRepository.save(task);
    }

    public Task updateTask(Long id, Task updatedTask) {
    	logger.info("Updating task: {}", updatedTask);        
        return taskRepository.findById(id).map(task -> {
            task.setTitle(updatedTask.getTitle());
            task.setDescription(updatedTask.getDescription());
            task.setDueDate(updatedTask.getDueDate());
            task.setStatus(updatedTask.getStatus());
            return taskRepository.save(task);
        }).orElse(null);
    }

    public void deleteTask(Long id) {
    	logger.info("Deleting task with ID: {}", id);        
        taskRepository.deleteById(id);
    }
    
    public List<Task> getPendingTasks() {
    	logger.info("Getting pending tasks");        
        return taskRepository.findByStatus(Task.Status.Pending);
    }

    public List<Task> getCompletedTasks() {
    	logger.info("Getting completed tasks");        
        return taskRepository.findByStatus(Task.Status.Completed);
    }
    
    public void markTaskAsCompleted(Long id) {
        Optional<Task> taskOpt = taskRepository.findById(id);
        taskOpt.ifPresent(task -> {
            task.setStatus(Task.Status.Completed);
            taskRepository.save(task);
        });
    }

}
