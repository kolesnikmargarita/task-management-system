package by.kolesnik.springsecuritytms.controller;

import by.kolesnik.springsecuritytms.dto.task.TaskCreateDto;
import by.kolesnik.springsecuritytms.dto.task.TaskGetBasicDto;
import by.kolesnik.springsecuritytms.dto.task.TaskUpdateDto;
import by.kolesnik.springsecuritytms.dto.task.TaskGetDto;
import by.kolesnik.springsecuritytms.facade.TaskFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskFacade taskFacade;

    @GetMapping
    public List<TaskGetDto> findAll() {
        return taskFacade.findAll();
    }

    @GetMapping("/{id}")
    public TaskGetDto findById(@PathVariable Long id) {
        return taskFacade.findById(id);
    }

    @PostMapping
    public TaskGetBasicDto create(@RequestBody TaskCreateDto dto) {
        return taskFacade.create(dto);
    }

    @PatchMapping("/{id}")
    public TaskGetDto update(@PathVariable Long id, @RequestBody TaskUpdateDto dto) {
        // todo: update task (only if this task created by current user)
        return taskFacade.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        taskFacade.delete(id);
    }
}
