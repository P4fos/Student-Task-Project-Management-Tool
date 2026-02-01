package edu.aitu.oop3.repositories.interfaces;
import java.util.List;

public interface IGenericRepository<T> {
    T getById(int id);
    List<T> getAll();
}