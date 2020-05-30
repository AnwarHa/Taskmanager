package tm.taskmanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import tm.taskmanager.dto.SubtaskDTO;
import tm.taskmanager.dto.TaskDTO;
import tm.taskmanager.service.Task;
import tm.taskmanager.service.TaskServiceImp;

import javax.validation.Valid;

@Controller
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskServiceImp taskServiceImp;

    @GetMapping("")
    public String tasks(Model model) {
        model.addAttribute("tasks", taskServiceImp.getTaskDTOs());
        return "tasks.html";
    }

    @GetMapping("/{id}")
    public String taskDetails(Model model, @PathVariable("id") Integer id) {
        model.addAttribute("taskId", id);
        model.addAttribute("task", taskServiceImp.getTaskDTO(id));
        model.addAttribute("subtasks", taskServiceImp.getSubtaskDTOs(id));
        return "taskDetails.html";
    }

    @GetMapping("/new")
    public String newTaskPage() {
        return "new.html";
    }

    @PostMapping("/new")
    public String addTask(@ModelAttribute TaskDTO taskdto) {
        taskServiceImp.addTask(taskdto);
        return "redirect:/tasks";
    }

    @GetMapping("/edit/{id}")
    public String editTaskPage(Model model, @PathVariable("id") Integer id) {
        model.addAttribute("task", taskServiceImp.getTaskDTO(id));
        return "editTask.html";
    }

    @RequestMapping("/edit/{id}")
    public String editTask(@ModelAttribute @Valid TaskDTO taskDTO, BindingResult bindingResult, @PathVariable("id") Integer id) {
        if (bindingResult.hasErrors()) {
            return "editTask.html";
        }
        taskServiceImp.editTask(taskDTO, id);
        return "redirect:/tasks/" + id;
    }

    @GetMapping("/{id}/sub/create")
    public String addSubtaskPage(Model model, @PathVariable("id") Integer id) {
        model.addAttribute("task", taskServiceImp.getTaskDTO(id));
        return "sub.html";
    }


    @PostMapping("/{id}/sub/create")
    public String addSubtask(@ModelAttribute SubtaskDTO subtask, @PathVariable("id") Integer id) {
        taskServiceImp.addSubtask(id, subtask);
        return "redirect:/tasks/" + id;
    }

    @GetMapping("/")
    public String home() {
        return "index.html";
    }

}
