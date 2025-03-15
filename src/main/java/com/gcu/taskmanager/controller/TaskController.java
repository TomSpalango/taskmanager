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

    // Handle BOTH "/" and "/tasks"
    @GetMapping({"/", "/tasks"})
    public String showTaskList(Model model) {
        List<Task> tasks = taskService.getAllTasks();
        model.addAttribute("tasks", tasks);
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
    public String showEditTaskForm(@PathVariable Long id, Model model) {
        Task task = taskService.getTaskById(id).orElse(null);
        if (task == null) {
            return "redirect:/tasks";
        }
        model.addAttribute("task", task);
        return "edit-task";
    }

    @PostMapping("/tasks/edit/{id}")
    public String updateTask(@PathVariable Long id, @ModelAttribute Task updatedTask) {
        taskService.updateTask(id, updatedTask);
        return "redirect:/tasks";
    }

    @PostMapping("/tasks/complete/{id}")
    public String markTaskAsCompleted(@PathVariable Long id) {
        Task task = taskService.getTaskById(id).orElse(null);
        if (task != null) {
            task.setStatus(Task.Status.Completed);
            taskService.updateTask(id, task);
        }
        return "redirect:/tasks";
    }

    @PostMapping("/tasks/delete/{id}")
    public String deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return "redirect:/tasks";
    }

    @GetMapping("/tasks/completed")
    public String showCompletedTasks(Model model) {
        List<Task> tasks = taskService.getAllTasks().stream()
                .filter(task -> task.getStatus() == Task.Status.Completed)
                .toList();
        model.addAttribute("tasks", tasks);
        return "completed-tasks";
    }
}
