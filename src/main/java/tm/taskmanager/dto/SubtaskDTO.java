package tm.taskmanager.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class SubtaskDTO {
    private int id;
    @NotNull
    @NotEmpty
    private String description;
    @NotNull
    @NotEmpty
    private String title;
    private TaskDTO parent;

    public SubtaskDTO(){

    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public TaskDTO getParent() {
        return parent;
    }

    public void setParent(TaskDTO parent) {
        this.parent = parent;
    }
}
