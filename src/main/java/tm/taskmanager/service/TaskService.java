package tm.taskmanager.service;

import tm.taskmanager.dto.SubtaskDTO;
import tm.taskmanager.dto.TaskDTO;

public interface TaskService {
    TaskDTO getDTOfromTask(Task task);

    void addTaskWithDTO(TaskDTO taskDTO);

    void editTaskWithDTO(TaskDTO taskDTO, int id);

    void addSubtaskWithDTO(SubtaskDTO subtaskDTO, int id);
}
