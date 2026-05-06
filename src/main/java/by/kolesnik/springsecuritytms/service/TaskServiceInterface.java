package by.kolesnik.springsecuritytms.service;

import by.kolesnik.springsecuritytms.entity.Task;

import java.util.Collection;

public interface TaskServiceInterface {

    Collection<Task> findAll();

    Collection<Task> findAllForCurrentUser();

    Task findById(Long id);

    Task findByIdForCurrentUser(Long id);

    Task create(Task task);

    Task update(Task task);

    void delete(Long id);
}
