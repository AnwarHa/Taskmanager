package tm.taskmanager;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tm.taskmanager.dto.SubtaskDTO;
import tm.taskmanager.dto.TaskDTO;
import tm.taskmanager.domain.Subtask;
import tm.taskmanager.domain.Task;
import tm.taskmanager.service.TaskServiceImp;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class TaskServiceImpTest {

    @Autowired
    private TaskServiceImp taskServiceImp;

    @Test
    public void testGetDTOfromTask() {
        Task task = new Task();
        task.setTitle("title_ex");
        task.setDescription("title_ex");
        task.setLocalDateTime(LocalDateTime.now());

        TaskDTO taskDTO = taskServiceImp.getDTOfromTask(task);

        assertNotNull(taskDTO);
        assertEquals(taskDTO.getTitle(), task.getTitle());
        assertEquals(taskDTO.getDescription(), task.getDescription());
        assertEquals(taskDTO.getLocalDateTime(), task.getLocalDateTime());
    }

    @Test
    public void testAddTaskWithDTO() {
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setTitle("dto_ex");
        taskDTO.setDescription("dto_ex");
        taskDTO.setLocalDateTime(LocalDateTime.now());

        taskServiceImp.addTaskWithDTO(taskDTO);

        // Eerste task die toegevoegd wordt: via Sequence generator -> geeft aan dat index = 1 zal zijn.
        Task task = taskServiceImp.getTaskRepository().findById(1).get();

        assertNotNull(task);
        assertEquals(taskDTO.getTitle(), task.getTitle());
        assertEquals(taskDTO.getDescription(), task.getDescription());
        assertEquals(taskDTO.getLocalDateTime(), task.getLocalDateTime());
    }

    @Test
    public void testEditTaskWithDTO() {
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setTitle("editTitle_ex");
        taskDTO.setDescription("editDescr_ex");
        taskDTO.setLocalDateTime(LocalDateTime.of(1996, 8, 4, 12, 4, 12));

        taskServiceImp.editTaskWithDTO(taskDTO, 1);

        Task task = taskServiceImp.getTaskRepository().findById(1).get();

        assertNotNull(task);
        assertEquals(taskDTO.getTitle(), task.getTitle());
        assertEquals(taskDTO.getDescription(), task.getDescription());
        assertEquals(taskDTO.getLocalDateTime(), task.getLocalDateTime());
    }

    @Test
    public void testAddSubtaskWithDTO() {
        SubtaskDTO subtaskDTO = new SubtaskDTO();
        subtaskDTO.setTitle("stdto_title");
        subtaskDTO.setDescription("stdto_descr");

        taskServiceImp.addSubtaskWithDTO(subtaskDTO, 1);

        Task task = taskServiceImp.getTaskRepository().findById(1).get();
        Subtask subtask = task.getSubtasks().get(0);

        assertNotNull(subtask);
        assertEquals(subtask.getTitle(), subtaskDTO.getTitle());
        assertEquals(subtask.getDescription(), subtaskDTO.getDescription());
        assertEquals(subtask.getParent(), task);
    }
}
