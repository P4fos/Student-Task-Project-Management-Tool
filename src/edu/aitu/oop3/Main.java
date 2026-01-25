package edu.aitu.oop3;

import edu.aitu.oop3.data.IDB;
import edu.aitu.oop3.data.DatabaseConnection;
import edu.aitu.oop3.entities.Task;
import edu.aitu.oop3.entities.User;
import edu.aitu.oop3.exceptions.TaskNotFoundException;
import edu.aitu.oop3.repositories.ITaskRepository;
import edu.aitu.oop3.repositories.TaskRepository;
import edu.aitu.oop3.repositories.UserRepository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        IDB db = new DatabaseConnection();
        ITaskRepository taskRepo = new TaskRepository(db);
        UserRepository userRepo = new UserRepository(db);
        Scanner scanner = new Scanner(System.in);

        User currentUser = null;

        System.out.println("Welcome to Student Task Manager!");


        while (currentUser == null) {
            System.out.print("Enter your email to login: ");
            String email = scanner.nextLine().trim();

            User existingUser = userRepo.getUserByEmail(email);

            if (existingUser != null) {
                currentUser = existingUser;
                System.out.println("Welcome back, " + currentUser.getUsername() + "!");
            } else {
                System.out.println("User not found. Let's register you!");
                System.out.print("Enter your Name: ");
                String name = scanner.nextLine();

                if (userRepo.createUser(name, email)) {
                    System.out.println("Registration successful! Logging in...");
                    currentUser = userRepo.getUserByEmail(email);
                } else {
                    System.out.println("Error creating user. Try again.");
                }
            }
        }


        while (true) {

            System.out.println("\n--- Menu for " + currentUser.getUsername() + " (Level " + (currentUser.getXp()/300 + 1) + ") ---");
            System.out.println("1. Add Task ");
            System.out.println("2. List My Tasks");
            System.out.println("3. Find Task by ID");
            System.out.println("4. Delete Task");
            System.out.println("5. Mark Task DONE ");
            System.out.println("6. Show My Profile");
            System.out.println("7. Exit");
            System.out.print("Enter choice: ");

            if (scanner.hasNextInt()) {
                int choice = scanner.nextInt();
                scanner.nextLine();

                try {
                    if (choice == 1) {
                        System.out.print("Enter Task Title: ");
                        String title = scanner.nextLine();
                        System.out.print("Enter Description: ");
                        String desc = scanner.nextLine();


                        Task t = new Task(title, desc, new Timestamp(System.currentTimeMillis() + 90000000), 1, currentUser.getId());

                        if(taskRepo.createTask(t)) {
                            System.out.println("New quest added!");
                        }

                    } else if (choice == 2) {

                        List<Task> tasks = taskRepo.getTasksByUserId(currentUser.getId());

                        if (tasks.isEmpty()) {
                            System.out.println("No quests yet. Add one!");
                        } else {
                            for (Task t : tasks) {
                                System.out.println(t);
                            }
                        }

                    } else if (choice == 3) {
                        System.out.print("Enter Task ID: ");
                        int id = scanner.nextInt();
                        Task t = taskRepo.getTaskById(id);

                        if (t != null && t.getUserId() == currentUser.getId()) {
                            System.out.println(t);
                        } else {
                            System.out.println("Task not found or access denied.");
                        }

                    } else if (choice == 4) {
                        System.out.print("Enter Task ID to delete: ");
                        int id = scanner.nextInt();
                        Task t = taskRepo.getTaskById(id);

                        if (t != null && t.getUserId() == currentUser.getId()) {
                            taskRepo.deleteTask(id);
                            System.out.println("Task deleted.");
                        } else {
                            System.out.println("Error: Task not found or you don't have permission.");
                        }

                    } else if (choice == 5) {
                        System.out.print("Enter Task ID to finish: ");
                        int id = scanner.nextInt();
                        scanner.nextLine();

                        Task task = taskRepo.getTaskById(id);

                        if (task != null && task.getUserId() == currentUser.getId()) {
                            if (!task.getStatus().equals("DONE")) {
                                task.setStatus("DONE");
                                if (taskRepo.updateTask(task)) {
                                    System.out.println("Quest Complete!");
                                    userRepo.addXp(currentUser.getId(), 50);
                                    currentUser = userRepo.getUserById(currentUser.getId());
                                }
                            } else {
                                System.out.println("You already finished this task!");
                            }
                        } else {
                            System.out.println("Task not found or access denied.");
                        }

                    } else if (choice == 6) {
                        currentUser = userRepo.getUserById(currentUser.getId());
                        System.out.println("--- Your Profile ---");
                        System.out.println(currentUser);
                        System.out.println("--------------------");

                    } else if (choice == 7) {
                        break;
                    }
                } catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
                }
            } else {
                scanner.next();
            }
        }
    }
}