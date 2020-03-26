package tm.taskmanager.service;

import tm.taskmanager.dto.TaskDTO;

import java.util.List;

public interface TaskService {
    List<TaskDTO> getTaskDTOs();

    void addTask(TaskDTO taskDTO);
}
