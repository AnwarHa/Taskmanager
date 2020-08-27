package tm.taskmanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import tm.taskmanager.dto.SubtaskDTO;
import tm.taskmanager.dto.TaskDTO;
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
        try {
            model.addAttribute("task", taskServiceImp.getDTOfromTaskID(id));
            model.addAttribute("subtasks", taskServiceImp.getDTOfromTaskID(id).getSubtasks());
        }catch (Exception e){
            model.addAttribute("error", "Task not found");
            return "index.html";
        }
        return "taskDetails.html";
    }

    @GetMapping("/new")
    public String newTaskPage() {
        return "new.html";
    }

    @GetMapping("/edit/{id}")
    public String editTaskPage(Model model, @PathVariable("id") Integer id) {
        model.addAttribute("task", taskServiceImp.getDTOfromTaskID(id));
        return "editTask.html";
    }

    @GetMapping("/{id}/sub/create")
    public String addSubtaskPage(Model model, @PathVariable("id") Integer id) {
        model.addAttribute("task", taskServiceImp.getDTOfromTaskID(id));
        return "sub.html";
    }


    @PostMapping("/new")
    public String addTask(@ModelAttribute("taskdto") @Valid TaskDTO taskdto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "new.html";
        }
        taskServiceImp.addTaskWithDTO(taskdto);
        return "redirect:/tasks";
    }

    @PostMapping("/edit/{id}")
    public String editTask(@ModelAttribute("taskDTO") @Valid TaskDTO taskDTO, BindingResult bindingResult, @PathVariable("id") Integer id) {
        if (bindingResult.hasErrors()) {
            return "redirect:/tasks/edit/" + id;
        }
        taskServiceImp.editTaskWithDTO(taskDTO, id);
        return "redirect:/tasks/" + id;
    }

    @PostMapping("/{id}/sub/create")
    public String addSubtask(@ModelAttribute("subtaskDTO") @Valid SubtaskDTO subtaskDTO, BindingResult bindingResult, @PathVariable("id") Integer id) {
        if (bindingResult.hasErrors()) {
            return "redirect:/tasks/" + id + "/sub/create";
        }
        taskServiceImp.addSubtaskWithDTO(subtaskDTO, id);
        return "redirect:/tasks/" + id;
    }
}
