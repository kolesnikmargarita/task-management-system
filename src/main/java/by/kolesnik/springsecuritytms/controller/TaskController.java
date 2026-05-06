package by.kolesnik.springsecuritytms.controller;

import by.kolesnik.springsecuritytms.controller.openapi.TaskOpenApi;
import by.kolesnik.springsecuritytms.dto.task.*;
import by.kolesnik.springsecuritytms.enums.CacheMode;
import by.kolesnik.springsecuritytms.facade.TaskFacade;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@SecurityScheme(type = SecuritySchemeType.HTTP, name = "bearerAuth", scheme = "bearer", bearerFormat = "JWT")
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/tasks")
@EnableMethodSecurity
@RequiredArgsConstructor
public class TaskController implements TaskOpenApi {

    private final TaskFacade taskFacade;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public List<TaskGetDto> findAll(@RequestParam(value = "cacheMode", defaultValue = "NONE_CACHE") CacheMode cacheMode) {
        return taskFacade.findAll(cacheMode);
    }

    @GetMapping("/my_tasks")
    @Override
    public List<TaskGetDto> findAllForCurrentUser(@RequestParam(value = "cacheMode", defaultValue = "NONE_CACHE") CacheMode cacheMode) {
        return taskFacade.findAllForCurrentUser(cacheMode);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public TaskGetDto findById(@PathVariable Long id, @RequestParam(value = "cacheMode", defaultValue = "NONE_CACHE") CacheMode cacheMode) {
        return taskFacade.findById(id, cacheMode);
    }

    @GetMapping("/my_tasks/{id}")
    @Override
    public TaskGetDto findByIdForCurrentUser(@PathVariable Long id, @RequestParam(value = "cacheMode", defaultValue = "NONE_CACHE") CacheMode cacheMode) {
        return taskFacade.findByIdForCurrentUser(id, cacheMode);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public TaskGetBasicDto create(@RequestBody TaskCreateDto dto, @RequestParam(value = "cacheMode", defaultValue = "NONE_CACHE") CacheMode cacheMode) {
        return taskFacade.create(dto, cacheMode);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public TaskGetDto update(@PathVariable Long id, @RequestBody TaskUpdateDto dto, @RequestParam(value = "cacheMode", defaultValue = "NONE_CACHE") CacheMode cacheMode) {
        return taskFacade.update(id, dto, cacheMode);
    }

    @PatchMapping("/{id}/status")
    @Override
    public TaskGetDto updateStatus(@PathVariable Long id, @RequestBody TaskUserUpdateDto dto, @RequestParam(value = "cacheMode", defaultValue = "NONE_CACHE") CacheMode cacheMode) {
        return taskFacade.updateStatus(id, dto, cacheMode);
    }


    @DeleteMapping("/{id}")
    @Override
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ADMIN')")
    public void delete(@PathVariable Long id, @RequestParam(value = "cacheMode", defaultValue = "NONE_CACHE") CacheMode cacheMode) {
        taskFacade.delete(id, cacheMode);
    }
}
