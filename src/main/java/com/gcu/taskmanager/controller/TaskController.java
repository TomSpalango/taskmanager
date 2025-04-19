package com.gcu.taskmanager.controller;

import com.gcu.taskmanager.models.Task;
import com.gcu.taskmanager.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class TaskController {

    private static final Logger logger = LoggerFactory.getLogger(TaskController.class);

    @Autowired
    private TaskService taskService;

    @GetMapping("/")
    public String index(@RequestParam(name = "showCompleted", required = false, defaultValue = "false") boolean showCompleted, Model model) {
        logger.info("TaskController.index() - showCompleted: {}", showCompleted);

        model.addAttribute("showCompleted", showCompleted);
        model.addAttribute("tasks", taskService.getAllTasks());

        return "index";
    }

    @GetMapping("/tasks/new")
    public String newTaskForm(Model model) {
        logger.info("TaskController.newTaskForm() - Loading form to create task");
        model.addAttribute("task", new Task());
        return "new-task";
    }

    @PostMapping("/tasks")
    public String createTask(@ModelAttribute Task task) {
        logger.info("TaskController.createTask() - Creating task: {}", task);
        taskService.createTask(task);
        return "redirect:/";
    }

    @GetMapping("/tasks/edit/{id}")
    public String editTaskForm(@PathVariable Long id, Model model) {
        logger.info("TaskController.editTaskForm() - Editing task with id: {}", id);
        return taskService.getTaskById(id)
                .map(task -> {
                    model.addAttribute("task", task);
                    return "edit-task";
                })
                .orElse("redirect:/");
    }

    @PostMapping("/tasks/update/{id}")
    public String updateTask(@PathVariable Long id, @ModelAttribute Task task) {
        logger.info("TaskController.updateTask() - Updating task with id: {}", id);
        taskService.updateTask(id, task);
        return "redirect:/";
    }

    @GetMapping("/tasks/delete/{id}")
    public String deleteTask(@PathVariable Long id) {
        logger.info("TaskController.deleteTask() - Deleting task with id: {}", id);
        taskService.deleteTask(id);
        return "redirect:/";
    }
}
