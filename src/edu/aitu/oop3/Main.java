package edu.aitu.oop3;

import edu.aitu.oop3.components.shared.IDB;
import edu.aitu.oop3.components.shared.DatabaseConnection;
import edu.aitu.oop3.TaskTrackingComponent.Task;
import edu.aitu.oop3.TaskTrackingComponent.TaskFactory;
import edu.aitu.oop3.ProjectManagementComponent.User;
import edu.aitu.oop3.exceptions.UserAlreadyExistsException;
import edu.aitu.oop3.exceptions.ValidationException;
import edu.aitu.oop3.TaskTrackingComponent.ITaskRepository;
import edu.aitu.oop3.TaskTrackingComponent.TaskRepository;
import edu.aitu.oop3.ProjectManagementComponent.UserRepository;
import edu.aitu.oop3.services.EmailNotificationService;
import edu.aitu.oop3.services.INotificationService;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        IDB db = DatabaseConnection.getInstance();

        ITaskRepository taskRepo = new TaskRepository(db);
        UserRepository userRepo = new UserRepository(db);

        INotificationService notifier = new EmailNotificationService();

        Scanner scanner = new Scanner(System.in);

        User currentUser = null;

        System.out.println("Welcome to Student Task Manager!");

        while (currentUser == null) {
            System.out.println("\n--- LOGIN / REGISTER ---");
            System.out.print("Enter your email: ");
            String email = scanner.nextLine().trim();

            User existingUser = userRepo.getUserByEmail(email);

            if (existingUser != null) {
                currentUser = existingUser;
                System.out.println("Welcome back, " + currentUser.getUsername() + "!");
            } else {
                System.out.println("User not found. Let's register you!");
                System.out.print("Enter your Name: ");
                String name = scanner.nextLine();

                try {
                    User newUser = new User(0, name, email, 0);
                    userRepo.createUser(newUser);

                    System.out.println("Registration successful! Logging in...");
                    currentUser = userRepo.getUserByEmail(email);
                } catch (UserAlreadyExistsException e) {
                    System.out.println("REGISTRATION ERROR: " + e.getMessage());
                } catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
                }
            }
        }

        while (true) {
            System.out.println("\n--- Menu for " + currentUser.getUsername() + " (Level " + (currentUser.getXp() / 300 + 1) + ") ---");
            System.out.println("1. Add Task");
            System.out.println("2. List My Tasks");
            System.out.println("3. Find Task by ID");
            System.out.println("4. Delete Task");
            System.out.println("5. Mark Task DONE");
            System.out.println("6. Show My Profile");
            System.out.println("7. Show ONLY Completed Tasks");
            System.out.println("0. Exit");
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

                        System.out.print("Is this task URGENT? (yes/no): ");
                        String type = scanner.nextLine().trim();

                        Task t;
                        if (type.equalsIgnoreCase("yes")) {
                            t = TaskFactory.createUrgentTask(currentUser.getId(), title, desc);
                        } else {
                            t = TaskFactory.createSimpleTask(currentUser.getId(), title, desc);
                        }

                        try {
                            taskRepo.createTask(t);
                            System.out.println("New quest added!");

                            // 2. Отправляем уведомление
                            notifier.sendNotification("New Task Created: " + title); // <--- NEW !!!

                        } catch (ValidationException e) {
                            System.out.println("VALIDATION ERROR: " + e.getMessage());
                        }

                    } else if (choice == 2) {
                        List<Task> tasks = taskRepo.getTasksByUserId(currentUser.getId());
                        if (tasks.isEmpty()) {
                            System.out.println("No quests yet.");
                        } else {
                            for (Task t : tasks) {
                                System.out.println(t);
                            }
                        }

                    } else if (choice == 3) {
                        System.out.print("Enter Task ID: ");
                        int id = scanner.nextInt();
                        Task t = taskRepo.getById(id);
                        if (t != null && t.getUserId() == currentUser.getId()) {
                            System.out.println(t);
                        } else {
                            System.out.println("Task not found or access denied.");
                        }

                    } else if (choice == 4) {
                        System.out.print("Enter Task ID to delete: ");
                        int id = scanner.nextInt();
                        Task t = taskRepo.getById(id);
                        if (t != null && t.getUserId() == currentUser.getId()) {
                            taskRepo.deleteTask(id);
                            System.out.println("Task deleted.");
                        } else {
                            System.out.println("Error: Task not found or access denied.");
                        }

                    } else if (choice == 5) {
                        System.out.print("Enter Task ID to finish: ");
                        int id = scanner.nextInt();
                        Task task = taskRepo.getById(id);

                        if (task != null && task.getUserId() == currentUser.getId()) {
                            if (!"DONE".equals(task.getStatus())) {
                                task.setStatus("DONE");
                                System.out.println("Quest Complete! +50 XP");
                                userRepo.addXp(currentUser.getId(), 50);
                                currentUser = userRepo.getUserById(currentUser.getId());

                                notifier.sendNotification("Task Completed! You gained XP.");

                            } else {
                                System.out.println("You already finished this task!");
                            }
                        } else {
                            System.out.println("Access denied or task not found.");
                        }

                    } else if (choice == 6) {
                        currentUser = userRepo.getUserById(currentUser.getId());
                        System.out.println("--- Your Profile ---");
                        System.out.println(currentUser);

                    } else if (choice == 7) {
                        System.out.println("--- Completed Tasks ---");
                        List<Task> myTasks = taskRepo.getTasksByUserId(currentUser.getId());

                        myTasks.stream()
                                .filter(task -> "DONE".equalsIgnoreCase(task.getStatus()))
                                .forEach(System.out::println);

                    } else if (choice == 0) {
                        System.out.println("Goodbye!");
                        break;
                    } else {
                        System.out.println("Invalid option.");
                    }

                } catch (Exception e) {
                    System.out.println("System Error: " + e.getMessage());
                    e.printStackTrace();
                }
            } else {
                scanner.next();
                System.out.println("Please enter a valid number.");
            }
        }
    }
}