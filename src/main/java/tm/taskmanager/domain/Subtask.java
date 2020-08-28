package tm.taskmanager.domain;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
public class Subtask implements Serializable {
    @Id
    @SequenceGenerator(name = "subtask_gen", initialValue = 100)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "subtask_gen")
    private int id;
    @NotNull
    @NotEmpty
    private String description;
    @NotNull
    @NotEmpty
    private String title;
    @ManyToOne
    private Task parent;

    public Subtask(){

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

    public Task getParent() {
        return parent;
    }

    public void setParent(Task parent) {
        this.parent = parent;
    }
}
