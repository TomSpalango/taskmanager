package com.gcu.taskmanager.controller;

import com.gcu.taskmanager.models.Task;
import com.gcu.taskmanager.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @GetMapping("")
    public String redirectToTasksRoot() {
        return "redirect:/tasks/";
    }
    
    @GetMapping("/")
    public String index(@RequestParam(value = "filter", required = false) String filter, Model model) {
        boolean showCompleted = "completed".equalsIgnoreCase(filter);

        List<Task> pendingTasks = taskService.getPendingTasks();
        model.addAttribute("pendingTasks", pendingTasks);

        if (showCompleted) {
            List<Task> completedTasks = taskService.getCompletedTasks();
            model.addAttribute("completedTasks", completedTasks);
        }

        model.addAttribute("showCompleted", showCompleted);
        return "index";
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

    @GetMapping("/delete/{id}")
    public String deleteTask(@PathVariable("id") Long id) {
        taskService.deleteTask(id);
        return "redirect:/tasks";
    }
}
