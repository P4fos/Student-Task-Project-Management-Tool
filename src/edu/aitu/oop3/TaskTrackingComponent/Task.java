package edu.aitu.oop3.TaskTrackingComponent;

public class Task {
    private int id;
    private String title;
    private String description;
    private int userId;
    private String status;
    public Task(int id, String title, String description, int userId, String status) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.userId = userId;
        this.status = status;
    }

    private Task(Builder builder) {
        this.id = builder.id;
        this.title = builder.title;
        this.description = builder.description;
        this.userId = builder.userId;
        this.status = "PENDING";
    }


    public static class Builder {
        private int id;
        private String title;
        private String description;
        private int userId;

        public Builder setId(int id) {
            this.id = id;
            return this;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder setUserId(int userId) {
            this.userId = userId;
            return this;
        }

        public Task build() {
            return new Task(this);
        }
    }


    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }


    @Override
    public String toString() {
        return "Task [id=" + id + ", title=" + title + ", status=" + status + "]";
    }
}