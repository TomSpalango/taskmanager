package com.gcu.taskmanager.controller;

import com.gcu.taskmanager.models.Task;
import com.gcu.taskmanager.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Controller
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;
    private static final Logger logger = LoggerFactory.getLogger(TaskController.class);

    @GetMapping("/")
    public String index(@RequestParam(value = "filter", required = false) String filter, Model model) {
    	logger.info("Accessed index page with filter: {}", filter);        
    	boolean showCompleted = "completed".equalsIgnoreCase(filter);
        model.addAttribute("showCompleted", showCompleted);
        model.addAttribute("pendingTasks", taskService.getPendingTasks());
        model.addAttribute("completedTasks", showCompleted ? taskService.getCompletedTasks() : null);
        return "index"; 
    }

    @GetMapping("")
    public String redirectToTasksRoot(@RequestParam(value = "filter", required = false) String filter) {
        if ("completed".equalsIgnoreCase(filter)) {
            return "redirect:/tasks/?filter=completed";
        }
        return "redirect:/tasks/";
    }

    @GetMapping("/new")
    public String showNewTaskForm(Model model) {
        model.addAttribute("task", new Task());
        return "new-task";
    }

    @PostMapping("/save")
    public String saveTask(@ModelAttribute Task task) {
        taskService.createTask(task);
        return "redirect:/tasks";
    }

    @GetMapping("/edit/{id}")
    public String showEditTaskForm(@PathVariable("id") Long id, Model model) {
        Optional<Task> taskOptional = taskService.getTaskById(id);
        if (taskOptional.isPresent()) {
            model.addAttribute("task", taskOptional.get());
            return "edit-task";
        } else {
            return "redirect:/tasks";
        }
    }

    @PostMapping("/update")
    public String updateTask(@ModelAttribute Task task) {
        taskService.updateTask(task.getId(), task);
        return "redirect:/tasks";
    }

    @PostMapping("/delete/{id}")
    public String deleteTask(@PathVariable("id") Long id) {
        taskService.deleteTask(id);
        return "redirect:/tasks";
    }

    @PostMapping("/complete/{id}")
    public String completeTask(@PathVariable Long id, @RequestParam(value = "filter", required = false) String filter) {
        taskService.markTaskAsCompleted(id);
        return "redirect:/tasks" + (filter != null ? "?filter=" + filter : "");
    }

}
