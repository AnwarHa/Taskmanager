package tm.taskmanager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tm.taskmanager.dto.SubtaskDTO;
import tm.taskmanager.dto.TaskDTO;
import tm.taskmanager.repo.TaskRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskServiceImp implements TaskService {
    // TaskRepository = een JPA repository met type <Task, Integer>
    private TaskRepository taskRepository;

    @Autowired
    public TaskServiceImp(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    // Aan de hand van een Task object, maken we een DTO object dat op onze website getoond kan worden.
    public TaskDTO createTaskDTO(Task task) {
        // We maken een nieuw DTO object aan en geven de waardes die we willen overdragen mee via setters.
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setId(task.getId());
        taskDTO.setTitle(task.getTitle());
        taskDTO.setDescription(task.getDescription());
        taskDTO.setLocalDateTime(task.getLocalDateTime());


        // Een Task kan Subtasks hebben, die ook DTO's moeten worden.
        // Let wel op; dit is enkel voor Tasks die al subtasks zouden hebben, vooraleer we POST doen.
        List<Subtask> subtasks = task.getSubtasks();
        List<SubtaskDTO> subtaskDTOs = new ArrayList<>();
        // Voor elke Subtask maak je een DTO.
        for (Subtask st : subtasks) {
            SubtaskDTO subtaskDTO = new SubtaskDTO();
            subtaskDTO.setTitle(st.getTitle());
            subtaskDTO.setDescription(st.getDescription());
            subtaskDTOs.add(subtaskDTO);
        }
        // De TaskDTO krijgt een lijst van SubtaskDTO's.
        taskDTO.setSubtasks(subtaskDTOs);
        return taskDTO;
    }

    // Geven een lijst terug van TaskDTO's.
    // In de repo wordt gezocht naar alle Task objecten. We zetten die objecten om naar DTO's en geven die
    // gezamenlijk terug in een lijst.
    public List<TaskDTO> getTaskDTOs() {
        return taskRepository.findAll().stream().map(task -> createTaskDTO(task)).collect(Collectors.toList());
    }

    // Zoek naar Task object in repo; en maak en geef DTO ervan terug.
    public TaskDTO getTaskDTO(int id) {
        Task task = taskRepository.findById(id).get();
        return createTaskDTO(task);
    }

    public int getNextId() {
        int tot = 1;
        for (Task task : taskRepository.findAll()) {
            if (task.getId() != 0) {
                tot++;
            }
        }
        return tot;
    }

    // Een Task object wordt aan de repo toegevoegd via een DTO.
    // De Task object wordt aangemaakt en ontvangt waardes van de DTO.
    public void addTask(TaskDTO taskDTO) {
        int id = getNextId();
        taskDTO.setId(id);
        Task task = new Task();
        task.setTitle(taskDTO.getTitle());
        task.setDescription(taskDTO.getDescription());
        task.setLocalDateTime(taskDTO.getLocalDateTime());
        task.setId(id);
        taskRepository.save(task);

    }

    // Een Task object wordt aangepast, door zijn id mee te geven en een ander Task object waar we data van
    // willen overnemen.
    public void editTask(TaskDTO taskDTO, int id) {
        Task t = taskRepository.findById(id).get();
        t.setDescription(taskDTO.getDescription());
        t.setTitle(taskDTO.getTitle());
        t.setLocalDateTime(taskDTO.getLocalDateTime());
        taskRepository.saveAndFlush(t);
    }

    public Task getTask(int id) {
        return taskRepository.findById(id).get();
    }

    // We willen Subtask object toevoegen aan een Task object via een ontvangen DTO.
    public void addSubtask(int id, SubtaskDTO subtaskDTO) {
        Task task = getTask(id);
        // Een nieuw Subtask object wordt aangemaakt en ontvangt waardes van de DTO.
        Subtask subtask = new Subtask();
        subtask.setTitle(subtaskDTO.getTitle());
        subtask.setDescription(subtaskDTO.getDescription());
        task.addSubtask(subtask);
        this.taskRepository.saveAndFlush(task);
    }

    public List<SubtaskDTO> getSubtaskDTOs(int id) {
        return getTaskDTO(id).getSubtasks();
    }

    public TaskRepository getTaskRepository() {
        return taskRepository;
    }
}
