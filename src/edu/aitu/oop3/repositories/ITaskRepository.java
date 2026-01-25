package edu.aitu.oop3.repositories;

import edu.aitu.oop3.entities.Task;
import edu.aitu.oop3.exceptions.TaskNotFoundException;
import java.util.List;

public interface ITaskRepository {
    boolean createTask(Task task);


    List<Task> getTasksByUserId(int userId);

    Task getTaskById(int id);
    boolean deleteTask(int id) throws TaskNotFoundException;
    boolean updateTask(Task task);
}