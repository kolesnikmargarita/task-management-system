package by.kolesnik.springsecuritytms.service;

import by.kolesnik.springsecuritytms.entity.Task;
import by.kolesnik.springsecuritytms.repository.TaskRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserService userService;

    public Collection<Task> findAll() {
        return taskRepository.findAll();
    }

    public Collection<Task> findAllForCurrentUser() {
        return taskRepository.findAllByCreator(userService.getCurrentUser());
    }

    public Task findById(Long id) {
        Optional<Task> optionalTask = taskRepository.findById(id);

        if(optionalTask.isEmpty()) {
            throw new EntityNotFoundException("task with id=" + id + " not found");
        }

        return optionalTask.get();
    }

    public Task findByIdForCurrentUser(Long id) {
        Optional<Task> optionalTask = taskRepository.findByIdAndCreator(id, userService.getCurrentUser());

        if(optionalTask.isEmpty()) {
            throw new EntityNotFoundException("task with id=" + id + " not found");
        }

        return optionalTask.get();
    }

    public Task create(Task task) {
        if(!task.getDeadlineDate().isAfter(task.getCreateDateTime())) {
            throw new EntityNotFoundException("deadline should be in future");
        }

        return taskRepository.save(task);
    }

    public Task update(Task task) {
        if(!task.getDeadlineDate().isAfter(task.getCreateDateTime())) {
            throw new EntityNotFoundException("deadline should be in future");
        }
        if(!task.getAssignedUser().equals(userService.getCurrentUser())) {
            throw new EntityNotFoundException("it is not your task");
        }

        return taskRepository.save(task);
    }

    public void delete(Long id) {
        taskRepository.deleteById(id);
    }
}
