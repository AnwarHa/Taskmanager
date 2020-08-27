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
    public void testGetDTOfromTask() {

    }
}
