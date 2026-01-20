import edu.aitu.oop3.data.IDB;
import edu.aitu.oop3.data.DatabaseConnection;
import edu.aitu.oop3.entities.Task;
import edu.aitu.oop3.exceptions.TaskNotFoundException;
import edu.aitu.oop3.repositories.ITaskRepository;
import edu.aitu.oop3.repositories.TaskRepository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        IDB db = new DatabaseConnection();
        ITaskRepository taskRepo = new TaskRepository(db);
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n--- Task Manager Menu ---");
            System.out.println("1. Add Task");
            System.out.println("2. List All Tasks");
            System.out.println("3. Find Task by ID");
            System.out.println("4. Delete Task");
            System.out.println("5. Exit");
            System.out.print("Enter choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            try {
                if (choice == 1) {
                    System.out.print("Enter Title: ");
                    String title = scanner.nextLine();
                    System.out.print("Enter Description: ");
                    String desc = scanner.nextLine();


                    Task t = new Task(title, desc, new Timestamp(System.currentTimeMillis() + 90000000), 1);
                    taskRepo.createTask(t);
                    System.out.println("Task created!");

                } else if (choice == 2) {
                    List<Task> tasks = taskRepo.getAllTasks();
                    for (Task t : tasks) {
                        System.out.println(t);
                    }

                } else if (choice == 3) {
                    System.out.print("Enter Task ID: ");
                    int id = scanner.nextInt();
                    Task t = taskRepo.getTaskById(id);
                    System.out.println(t != null ? t : "Task not found.");

                } else if (choice == 4) {
                    System.out.print("Enter Task ID to delete: ");
                    int id = scanner.nextInt();

                    try {
                        taskRepo.deleteTask(id);
                        System.out.println("Task deleted.");
                    } catch (TaskNotFoundException e) {
                        System.out.println("Error: " + e.getMessage());
                    }

                } else if (choice == 5) {
                    break;
                }
            } catch (Exception e) {
                System.out.println("Input Error: " + e.getMessage());
            }
        }
    }
}