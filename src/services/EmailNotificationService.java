package edu.aitu.oop3.services;

public class EmailNotificationService implements INotificationService {
    @Override
    public void sendNotification(String message) {
        System.out.println("📧 [EMAIL SENT]: " + message);
    }
}