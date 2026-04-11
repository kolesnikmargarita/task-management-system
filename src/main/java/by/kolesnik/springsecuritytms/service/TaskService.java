package by.kolesnik.springsecuritytms.service;

import by.kolesnik.springsecuritytms.entity.Task;
import by.kolesnik.springsecuritytms.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;

    public Collection<Task> findAll() {
        return taskRepository.findAll();
    }

    public Task findById(Long id) {
        Optional<Task> optionalTask = taskRepository.findById(id);

        if(optionalTask.isEmpty()) {
            throw new RuntimeException("task not found"); // todo: valid exception
        }

        return optionalTask.get();
    }

    public Task create(Task task) {
        if(!task.getDeadlineDate().isAfter(task.getCreateDateTime())) {
            throw new RuntimeException("dates is incorrect"); //todo: valid exception
        }

        return taskRepository.save(task);
    }

    public Task update(Task task) {
        if(!task.getDeadlineDate().isAfter(task.getCreateDateTime())) {
            throw new RuntimeException("dates is incorrect"); //todo: valid exception
        }

        return taskRepository.save(task);
    }

    public void delete(Long id) {
        taskRepository.deleteById(id);
    }
}
