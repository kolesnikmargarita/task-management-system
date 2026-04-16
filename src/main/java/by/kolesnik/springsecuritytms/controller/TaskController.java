package by.kolesnik.springsecuritytms.controller;

import by.kolesnik.springsecuritytms.controller.openapi.TaskOpenApi;
import by.kolesnik.springsecuritytms.dto.task.*;
import by.kolesnik.springsecuritytms.facade.TaskFacade;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
@EnableMethodSecurity
@RequiredArgsConstructor
public class TaskController implements TaskOpenApi {

    private final TaskFacade taskFacade;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public List<TaskGetDto> findAll() {
        return taskFacade.findAll();
    }

    @GetMapping("/my_tasks")
    @Override
    public List<TaskGetDto> findAllForCurrentUser() {
        return taskFacade.findAllForCurrentUser();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public TaskGetDto findById(@PathVariable Long id) {
        return taskFacade.findById(id);
    }

    @GetMapping("/my_tasks/{id}")
    @Override
    public TaskGetDto findByIdForCurrentUser(@PathVariable Long id) {
        return taskFacade.findByIdForCurrentUser(id);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public TaskGetBasicDto create(@RequestBody TaskCreateDto dto) {
        return taskFacade.create(dto);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public TaskGetDto update(@PathVariable Long id, @RequestBody TaskUpdateDto dto) {
        return taskFacade.update(id, dto);
    }

    @PatchMapping("/{id}/status")
    @Override
    public TaskGetDto updateStatus(@PathVariable Long id, @RequestBody TaskUserUpdateDto dto) {
        return taskFacade.updateStatus(id, dto);
    }


    @DeleteMapping("/{id}")
    @Override
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ADMIN')")
    public void delete(@PathVariable Long id) {
        taskFacade.delete(id);
    }
}
