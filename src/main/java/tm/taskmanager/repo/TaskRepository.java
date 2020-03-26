package tm.taskmanager.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import tm.taskmanager.service.Task;

public interface TaskRepository extends JpaRepository<Task, Integer> {
}
