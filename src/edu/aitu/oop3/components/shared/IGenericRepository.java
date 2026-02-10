package edu.aitu.oop3.components.shared;
import java.util.List;

public interface IGenericRepository<T> {
    T getById(int id);
    List<T> getAll();
}