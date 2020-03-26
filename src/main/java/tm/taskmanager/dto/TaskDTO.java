package tm.taskmanager.dto;

import org.springframework.format.annotation.DateTimeFormat;
import tm.taskmanager.service.Subtask;
import tm.taskmanager.service.Task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class TaskDTO {
    private int id;
    private String description, title;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime localDateTime;
    private List<SubtaskDTO> subtasks;

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

    public List<SubtaskDTO> getSubtasks() {
        if(subtasks == null){
            subtasks = new ArrayList<>();
        }
        return subtasks;
    }

    public void setSubtasks(List<SubtaskDTO> subtasks) {
        // Eigenlijk best gwn ArrayList gebruiken. Want als we dit setten, hebben we enkel List.
        // Ma ben te lui, gebruik dit toch niet.
        this.subtasks = subtasks;
    }

    public void addSubtaskDTO(SubtaskDTO subtaskDTO) {
        if(subtasks == null){
            subtasks = new ArrayList<>();
        }
        subtasks.add(subtaskDTO);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
