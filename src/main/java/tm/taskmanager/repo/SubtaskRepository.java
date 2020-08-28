package tm.taskmanager.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import tm.taskmanager.domain.Subtask;

public interface SubtaskRepository extends JpaRepository<Subtask, Integer> {
}
