package edu.aitu.oop3.TaskTrackingComponent;

public class TaskFactory {
    public static Task createSimpleTask(int userId, String title, String description) {
        return new Task.Builder()
                .setId(0)
                .setUserId(userId)
                .setTitle(title)
                .setDescription(description)
                .build();
    }

    public static Task createUrgentTask(int userId, String title, String description) {
        return new Task.Builder()
                .setId(0)
                .setUserId(userId)
                .setTitle("URGENT: " + title)
                .setDescription(description.toUpperCase() + " !!!")
                .build();
    }
}