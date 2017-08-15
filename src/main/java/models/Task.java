package models;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Task {

    private String description;
    private boolean completed;
    private LocalDateTime createdAt;
    private int id;
    private int categoryId;

    public Task(String description, int categoryId){
        this.description = description;
        this.completed = false;
        this.createdAt = LocalDateTime.now();
        this.categoryId = categoryId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Task task = (Task) o;

        if (completed != task.completed) return false;
        if (id != task.id) return false;
        if (categoryId != task.categoryId) return false;
        if (!description.equals(task.description)) return false;
        return description.equals(task.description);
//        return createdAt != null ? createdAt.equals(task.createdAt) : task.createdAt == null;
    }

    @Override
    public int hashCode() {
        int result = description.hashCode();
        result = 31 * result + (completed ? 1 : 0);
//        result = 31 * result + (createdAt != null ? createdAt.hashCode() : 0);
        result = 31 * result + id;
        result = 31 * result + categoryId;
        return result;
    }

    public int getCategoryId() {
        return this.categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isCompleted() {
        return completed;
    }

    public boolean getCompleted(){
        return this.completed;

    }public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }






}
