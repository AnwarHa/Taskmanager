package tm.taskmanager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tm.taskmanager.domain.Subtask;
import tm.taskmanager.domain.Task;
import tm.taskmanager.dto.SubtaskDTO;
import tm.taskmanager.dto.TaskDTO;
import tm.taskmanager.repo.SubtaskRepository;
import tm.taskmanager.repo.TaskRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskServiceImp implements TaskService {
    private TaskRepository taskRepository;
    private SubtaskRepository subtaskRepository;

    @Autowired
    public TaskServiceImp(TaskRepository taskRepository, SubtaskRepository subtaskRepository) {
        this.taskRepository = taskRepository;
        this.subtaskRepository = subtaskRepository;
    }

    public TaskDTO getDTOfromTask(Task task) {
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setId(task.getId());
        taskDTO.setTitle(task.getTitle());
        taskDTO.setDescription(task.getDescription());
        taskDTO.setLocalDateTime(task.getLocalDateTime());

        List<Subtask> subtasks = task.getSubtasks();
        for (Subtask subtask : subtasks) {
            SubtaskDTO subtaskDTO = new SubtaskDTO();
            subtaskDTO.setTitle(subtask.getTitle());
            subtaskDTO.setDescription(subtask.getDescription());
            subtaskDTO.setParent(taskDTO);
            taskDTO.addSubtaskDTO(subtaskDTO);
        }
        return taskDTO;
    }

    public TaskDTO getDTOfromTaskID(int id) {
        Task task = this.taskRepository.findById(id).get();
        return getDTOfromTask(task);
    }

    public List<TaskDTO> getTaskDTOs() {
        return taskRepository.findAll().stream().map(this::getDTOfromTask).collect(Collectors.toList());
    }

    public void addTaskWithDTO(TaskDTO taskDTO) {
        Task task = new Task();
        task.setTitle(taskDTO.getTitle());
        task.setDescription(taskDTO.getDescription());
        task.setLocalDateTime(taskDTO.getLocalDateTime());

        List<SubtaskDTO> subtaskDTOS = taskDTO.getSubtasks();
        for (SubtaskDTO subtaskDTO : subtaskDTOS) {
            Subtask subtask = new Subtask();
            subtask.setParent(task);
            subtask.setTitle(subtaskDTO.getTitle());
            subtask.setDescription(subtaskDTO.getDescription());
            task.addSubtask(subtask);
        }
        taskRepository.save(task);
    }

    public void editTaskWithDTO(TaskDTO taskDTO, int id) {
        Task task = taskRepository.findById(id).get();
        task.setTitle(taskDTO.getTitle());
        task.setDescription(taskDTO.getDescription());
        task.setLocalDateTime(taskDTO.getLocalDateTime());
        this.taskRepository.save(task);
    }

    public void addSubtaskWithDTO(SubtaskDTO subtaskDTO, int id) {
        Task task = taskRepository.findById(id).get();
        Subtask subtask = new Subtask();
        subtask.setParent(task);
        subtask.setTitle(subtaskDTO.getTitle());
        subtask.setDescription(subtaskDTO.getDescription());
        task.addSubtask(subtask);
        this.taskRepository.save(task);
        this.subtaskRepository.save(subtask);
    }

    // GETTERS

    public TaskRepository getTaskRepository() {
        return taskRepository;
    }

    public SubtaskRepository getSubtaskRepository() {
        return subtaskRepository;
    }
}
