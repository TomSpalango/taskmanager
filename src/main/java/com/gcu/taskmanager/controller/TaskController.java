package com.gcu.taskmanager.controller;

import com.gcu.taskmanager.models.Task;
import com.gcu.taskmanager.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class TaskController {

    @Autowired
    private TaskService taskService;

<<<<<<< HEAD
    @GetMapping("/")
    public String index(@RequestParam(name = "showCompleted", required = false, defaultValue = "false") boolean showCompleted, Model model) {
        logger.info("TaskController.index() - showCompleted: {}", showCompleted);

        model.addAttribute("showCompleted", showCompleted);
        model.addAttribute("tasks", taskService.getAllTasks());
=======
    // Handle BOTH "/" and "/tasks" with filtering
    @GetMapping({"/", "/tasks"})
    public String showTaskList(@RequestParam(value = "filter", required = false) String filter, Model model) {
        // Always show pending tasks
        List<Task> pendingTasks = taskService.getAllTasks().stream()
                .filter(task -> task.getStatus() == Task.Status.Pending)
                .toList();

        // Show completed tasks only if filter=completed
        List<Task> completedTasks = taskService.getAllTasks().stream()
                .filter(task -> task.getStatus() == Task.Status.Completed)
                .toList();

        boolean showCompleted = "completed".equals(filter);

        model.addAttribute("pendingTasks", pendingTasks);
        model.addAttribute("completedTasks", completedTasks);
        model.addAttribute("showCompleted", showCompleted);
>>>>>>> parent of f36aa8c (Added basic logging)

        return "index";
    }

    @GetMapping("/tasks/new")
    public String showNewTaskForm(Model model) {
        model.addAttribute("task", new Task());
        return "new-task";
    }

    @PostMapping("/tasks/new")
    public String saveNewTask(@ModelAttribute Task task) {
        taskService.createTask(task);
        return "redirect:/tasks";
    }

    @GetMapping("/tasks/edit/{id}")
    public String showEditTaskForm(@PathVariable Long id, 
                                   @RequestParam(value = "filter", required = false) String filter, 
                                   Model model) {
        Task task = taskService.getTaskById(id).orElse(null);
        if (task == null) {
            return "redirect:/tasks" + (filter != null ? "?filter=completed" : "");
        }
        model.addAttribute("task", task);
        model.addAttribute("filterCompleted", filter != null);
        return "edit-task";
    }

    @PostMapping("/tasks/edit/{id}")
    public String updateTask(@PathVariable Long id, 
                             @ModelAttribute Task updatedTask, 
                             @RequestParam(value = "filter", required = false) String filter) {
        taskService.updateTask(id, updatedTask);
        return "redirect:/tasks" + (filter != null ? "?filter=completed" : "");
    }

    @PostMapping("/tasks/complete/{id}")
    public String markTaskAsCompleted(@PathVariable Long id, @RequestParam(value = "filter", required = false) String filter) {
        Task task = taskService.getTaskById(id).orElse(null);
        if (task != null) {
            task.setStatus(Task.Status.Completed);
            taskService.updateTask(id, task);
        }
        return "redirect:/tasks" + (filter != null ? "?filter=completed" : "");
    }

    @PostMapping("/tasks/delete/{id}")
    public String deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return "redirect:/tasks";
    }
}
