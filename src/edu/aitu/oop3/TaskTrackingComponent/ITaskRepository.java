package edu.aitu.oop3.TaskTrackingComponent;

import edu.aitu.oop3.exceptions.ValidationException;
import edu.aitu.oop3.components.shared.IGenericRepository;

import java.util.List;

public interface ITaskRepository extends IGenericRepository<Task> {
    void createTask(Task task) throws ValidationException;
    List<Task> getTasksByUserId(int userId);
    void deleteTask(int id);
}