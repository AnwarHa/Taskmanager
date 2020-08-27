package tm.taskmanager.dto;

import org.springframework.format.annotation.DateTimeFormat;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class TaskDTO {
    private int id;
    @NotNull
    @NotEmpty
    private String description;
    @NotNull
    @NotEmpty
    private String title;
    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime localDateTime;
    private List<SubtaskDTO> subtasks;

    public TaskDTO(){

    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLocalDateTimeText() {
        String formatted = localDateTime.format(DateTimeFormatter.ofPattern("': due ' MMMM dd yyyy ' at ' h a"));
        return formatted;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }


    // SUBTASKS
    public List<SubtaskDTO> getSubtasks() {
        if (subtasks == null) {
            subtasks = new ArrayList<>();
        }
        return subtasks;
    }

    public void setSubtasks(List<SubtaskDTO> subtasks) {
        this.subtasks = subtasks;
    }

    public void addSubtaskDTO(SubtaskDTO subtaskDTO) {
        if (subtasks == null) {
            subtasks = new ArrayList<>();
        }
        subtasks.add(subtaskDTO);
    }

}
