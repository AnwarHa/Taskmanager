package tm.taskmanager;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tm.taskmanager.dto.SubtaskDTO;
import tm.taskmanager.dto.TaskDTO;
import tm.taskmanager.service.Subtask;
import tm.taskmanager.service.Task;
import tm.taskmanager.service.TaskServiceImp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class TaskServiceImpTest {

    @Autowired
    private TaskServiceImp taskServiceImp;

    @Test
    public void testGetTaskDTOs() {
        int id = taskServiceImp.getNextId();
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setId(id);
        taskDTO.setTitle("I am Test");
        taskDTO.setDescription("I am Legend");
        taskDTO.setLocalDateTime(LocalDateTime.of(1996, 4, 8, 12, 0, 0));

        // Lijst +1
        taskServiceImp.addTask(taskDTO);

        List<TaskDTO> taskDTOList = taskServiceImp.getTaskDTOs();

        assertNotNull(taskDTOList);
        assertFalse(taskDTOList.isEmpty());
        // In Notes zie je dat we 4 objecten aan de lijst hebben toegevoegd.
        assertEquals(4, taskDTOList.size());
        TaskDTO firstDTO = taskDTOList.get(0);
        assertNotNull(firstDTO);
    }

    @Test
    public void testCreateTaskDTO() {
        Task task = new Task();
        LocalDateTime localDateTime = LocalDateTime.of(2020, 12, 03, 14, 59, 0);
        String title = "test_title";
        String description = "test_description";
        int id = taskServiceImp.getNextId();
        task.setId(id);
        task.setTitle(title);
        task.setDescription(description);
        task.setLocalDateTime(localDateTime);

        ArrayList<Subtask> subtasks = new ArrayList<>();
        String titleSubtask1 = "Subtask title 1";
        String descriptionSubtask1 = "Subtask description 1";

        Subtask subtask1 = new Subtask();
        subtask1.setTitle(titleSubtask1);
        subtask1.setDescription(descriptionSubtask1);

        String titleSubtask2 = "Subtask title 2";
        String descriptionSubtask2 = "Subtask description 2";
        Subtask subtask2 = new Subtask();

        subtask2.setTitle(titleSubtask2);
        subtask2.setDescription(descriptionSubtask2);

        subtasks.add(subtask1);
        subtasks.add(subtask2);
        task.setSubtasks(subtasks);

        TaskDTO taskDTO = taskServiceImp.createTaskDTO(task);

        assertNotNull(taskDTO);
        assertNotNull(taskDTO.getSubtasks());
        assertEquals(id, taskDTO.getId());
        assertEquals(title, taskDTO.getTitle());
        assertEquals(description, taskDTO.getDescription());
        assertEquals(localDateTime, taskDTO.getLocalDateTime());
        assertEquals(titleSubtask1, taskDTO.getSubtasks().get(0).getTitle());
        assertEquals(descriptionSubtask1, taskDTO.getSubtasks().get(0).getDescription());
        assertEquals(titleSubtask2, taskDTO.getSubtasks().get(1).getTitle());
        assertEquals(descriptionSubtask2, taskDTO.getSubtasks().get(1).getDescription());
    }

    @Test
    public void testAddTask() {
        TaskDTO taskDTO = new TaskDTO();
        LocalDateTime localDateTime = LocalDateTime.of(2016, 12, 30, 13, 59, 0);
        String title = "add task title";
        String description = "add task description";
        int id = taskServiceImp.getNextId();
        taskDTO.setId(id);
        taskDTO.setLocalDateTime(localDateTime);
        taskDTO.setTitle(title);
        taskDTO.setDescription(description);

        // Lijst +1
        taskServiceImp.addTask(taskDTO);
        Task task = taskServiceImp.getTask(id);

        assertNotNull(task);
        assertNotNull(taskDTO);
        assertEquals(task.getId(), id);
        assertEquals(task.getDescription(), description);
        assertEquals(task.getTitle(), title);
        assertEquals(task.getLocalDateTime(), localDateTime);
    }

    @Test
    public void testEditTask() {
        int id = taskServiceImp.getNextId();

        Task task = new Task();
        task.setId(id);
        task.setTitle("boop");
        task.setDescription("bap");
        task.setLocalDateTime(LocalDateTime.now());

        // Lijst +1
        taskServiceImp.getTaskRepository().save(task);

        String newDescription = "new description";
        String newTitle = "new Title";
        LocalDateTime newLocalDateTime = LocalDateTime.of(1999, 12, 12, 1, 1, 1);

        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setTitle(newTitle);
        taskDTO.setDescription(newDescription);
        taskDTO.setLocalDateTime(newLocalDateTime);

        taskServiceImp.editTask(taskDTO, id);
        Task updatedTask = taskServiceImp.getTask(id);

        assertEquals(updatedTask.getTitle(), newTitle);
        assertEquals(updatedTask.getDescription(), newDescription);
        assertEquals(updatedTask.getLocalDateTime(), newLocalDateTime);
    }

    @Test
    public void testAddSubtask() {
        int id = taskServiceImp.getNextId();
        Task task = new Task();
        task.setId(id);
        task.setTitle("Main title");
        task.setDescription("Main description");
        task.setLocalDateTime(LocalDateTime.of(2000, 1, 1, 0, 0, 0));

        // Lijst +1
        taskServiceImp.getTaskRepository().save(task);

        SubtaskDTO subtaskDTO = new SubtaskDTO();
        String subTitle = "Subtask title";
        String subDescription = "Subtask description";
        subtaskDTO.setTitle(subTitle);
        subtaskDTO.setDescription(subDescription);

        taskServiceImp.addSubtask(id, subtaskDTO);

        Task taskInRepo = taskServiceImp.getTask(id);
        Subtask subtask = taskInRepo.getSubtasks().get(0);

        assertNotNull(taskInRepo);
        assertNotNull(subtask);
        assertEquals(subtask.getTitle(), subTitle);
        assertEquals(subtask.getDescription(), subDescription);
    }
}
