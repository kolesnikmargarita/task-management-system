package by.kolesnik.springsecuritytms.facade;

import by.kolesnik.springsecuritytms.dto.task.*;
import by.kolesnik.springsecuritytms.entity.Group;
import by.kolesnik.springsecuritytms.entity.Task;
import by.kolesnik.springsecuritytms.entity.User;
import by.kolesnik.springsecuritytms.enums.Status;
import by.kolesnik.springsecuritytms.mapper.TaskMapper;
import by.kolesnik.springsecuritytms.service.GroupService;
import by.kolesnik.springsecuritytms.service.TaskService;
import by.kolesnik.springsecuritytms.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Component
@RequiredArgsConstructor
public class TaskFacade {

    private final TaskService taskService;
    private final GroupService groupService;
    private final UserService userService;

    public List<TaskGetDto> findAll() {
        Collection<Task> tasks = taskService.findAll();
        return tasks.stream().map(TaskMapper::toGetDto).toList();
    }

    public List<TaskGetDto> findAllForCurrentUser() {
        Collection<Task> tasks = taskService.findAllForCurrentUser();
        return tasks.stream().map(TaskMapper::toGetDto).toList();
    }

    public TaskGetDto findById(Long id) {
        Task task = taskService.findById(id);
        return TaskMapper.toGetDto(task);
    }

    public TaskGetDto findByIdForCurrentUser(Long id) {
        Task task = taskService.findByIdForCurrentUser(id);
        return TaskMapper.toGetDto(task);
    }

    @Transactional
    public TaskGetBasicDto create(TaskCreateDto dto) {
        Task task = new Task();

        Group group = groupService.findById(dto.getGroupId());

        task.setDeadlineDate(dto.getDeadlineDate());
        task.setDescription(dto.getDescription());
        task.setPriority(dto.getPriority());
        task.setAssignedUser(userService.findByIdAndGroupId(dto.getAssignedUserId(), group));
        task.setGroup(group);

        User currentUser = userService.getCurrentUser();
        task.setCreator(currentUser);
        task.setCreateDateTime(LocalDateTime.now());
        task.setStatus(Status.CREATED);

        return TaskMapper.toGetBasicDto(taskService.create(task));
    }

    @Transactional
    public TaskGetDto update(Long id, TaskUpdateDto dto) {
        Task task = taskService.findById(id);
        // todo: проверку на задачу текущего пользователя

        if(!task.getAssignedUser().equals(userService.getCurrentUser())) {
            throw new RuntimeException("it is not your task"); //todo: valid exception
        }

        if(dto.getPriority() != null) {
            task.setPriority(dto.getPriority());
        }
        if(dto.getDescription() != null) {
            task.setDescription(dto.getDescription());
        }
        if(dto.getStatus() != null) {
            task.setStatus(dto.getStatus());
        }
        if(dto.getDeadlineDate() != null) {
            task.setDeadlineDate(dto.getDeadlineDate());
        }
        if(dto.getAssignedUserId() != null) {
            task.setAssignedUser(userService.findById(dto.getAssignedUserId()));
        }
        if(dto.getGroupId() != null) {
            task.setGroup(groupService.findById(dto.getGroupId()));
        }
        userService.findByIdAndGroupId(task.getAssignedUser().getId(), task.getGroup());

        return TaskMapper.toGetDto(taskService.update(task));
    }

    public TaskGetDto updateStatus(Long id, TaskUserUpdateDto dto) {
        Task task = taskService.findById(id);
        task.setStatus(dto.getStatus());
        return TaskMapper.toGetDto(taskService.update(task));
    }

    public void delete(Long id) {
        taskService.delete(id);
    }

}
