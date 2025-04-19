package com.gcu.taskmanager.controller;

import com.gcu.taskmanager.models.Task;
import com.gcu.taskmanager.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

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

        return "index";
    }

    @GetMapping("/tasks/new")
    public String newTaskForm(Model model) {
        logger.info("TaskController.newTaskForm() - Loading form to create task");
    public String showNewTaskForm(Model model) {
        model.addAttribute("task", new Task());
        return "new-task";
    }

    @PostMapping("/tasks")
    public String createTask(@ModelAttribute Task task) {
        logger.info("TaskController.createTask() - Creating task: {}", task);
    @PostMapping("/tasks/new")
    public String saveNewTask(@ModelAttribute Task task) {
        taskService.createTask(task);
        return "redirect:/";
        return "redirect:/tasks";
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

        logger.info("TaskController.updateTask() - Updating task with id: {}", id);
    @PostMapping("/tasks/complete/{id}")
    public String markTaskAsCompleted(@PathVariable Long id, @RequestParam(value = "filter", required = false) String filter) {
        Task task = taskService.getTaskById(id).orElse(null);
        if (task != null) {
            task.setStatus(Task.Status.Completed);
            taskService.updateTask(id, task);
        }
        return "redirect:/tasks" + (filter != null ? "?filter=completed" : "");
    }

    @GetMapping("/tasks/delete/{id}")
    @PostMapping("/tasks/delete/{id}")
    public String deleteTask(@PathVariable Long id) {
        logger.info("TaskController.deleteTask() - Deleting task with id: {}", id);
        taskService.deleteTask(id);
        return "redirect:/";
        return "redirect:/tasks";
    }
}
