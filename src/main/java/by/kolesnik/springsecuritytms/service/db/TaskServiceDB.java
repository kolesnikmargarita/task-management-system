package by.kolesnik.springsecuritytms.service.db;

import by.kolesnik.springsecuritytms.entity.Task;
import by.kolesnik.springsecuritytms.exception.DeadlineInPastException;
import by.kolesnik.springsecuritytms.exception.NotCurrentUserTaskException;
import by.kolesnik.springsecuritytms.repository.TaskRepository;
import by.kolesnik.springsecuritytms.service.TaskServiceInterface;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskServiceDB implements TaskServiceInterface {

    private final TaskRepository taskRepository;
    private final UserServiceDB userService;

    @Override
    public Collection<Task> findAll() {
        return taskRepository.findAll();
    }

    @Override
    public Collection<Task> findAllForCurrentUser() {
        return taskRepository.findAllByCreator(userService.getCurrentUser());
    }

    @Override
    public Task findById(Long id) {
        Optional<Task> optionalTask = taskRepository.findById(id);

        if(optionalTask.isEmpty()) {
            throw new EntityNotFoundException("task with id=" + id + " not found");
        }

        return optionalTask.get();
    }

    @Override
    public Task findByIdForCurrentUser(Long id) {
        Optional<Task> optionalTask = taskRepository.findByIdAndCreator(id, userService.getCurrentUser());

        if(optionalTask.isEmpty()) {
            throw new EntityNotFoundException("task with id=" + id + " not found");
        }

        return optionalTask.get();
    }

    @Override
    public Task create(Task task) {
        if(!task.getDeadlineDate().isAfter(task.getCreateDateTime())) {
            throw new DeadlineInPastException("deadline should be in future");
        }

        return taskRepository.save(task);
    }

    @Override
    public Task update(Task task) {
        if(!task.getDeadlineDate().isAfter(task.getCreateDateTime())) {
            throw new DeadlineInPastException("deadline should be in future");
        }
        if(!task.getAssignedUser().equals(userService.getCurrentUser())) {
            throw new NotCurrentUserTaskException("it is not your task");
        }

        return taskRepository.save(task);
    }

    @Override
    public void delete(Long id) {
        taskRepository.deleteById(id);
    }
}
