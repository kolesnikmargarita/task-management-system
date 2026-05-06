package by.kolesnik.springsecuritytms.facade;

import by.kolesnik.springsecuritytms.dto.task.*;
import by.kolesnik.springsecuritytms.entity.Group;
import by.kolesnik.springsecuritytms.entity.Task;
import by.kolesnik.springsecuritytms.entity.User;
import by.kolesnik.springsecuritytms.enums.CacheMode;
import by.kolesnik.springsecuritytms.enums.Status;
import by.kolesnik.springsecuritytms.exception.NotCurrentUserTaskException;
import by.kolesnik.springsecuritytms.mapper.TaskMapper;
import by.kolesnik.springsecuritytms.service.*;
import by.kolesnik.springsecuritytms.service.db.GroupServiceDB;
import by.kolesnik.springsecuritytms.service.db.TaskServiceDB;
import by.kolesnik.springsecuritytms.service.db.UserServiceDB;
import by.kolesnik.springsecuritytms.service.manual.ManualCachingGroupService;
import by.kolesnik.springsecuritytms.service.manual.ManualCachingTaskService;
import by.kolesnik.springsecuritytms.service.spring.SpringAnnotationCachingGroupService;
import by.kolesnik.springsecuritytms.service.spring.SpringAnnotationCachingTaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Component
@RequiredArgsConstructor
public class TaskFacade {

    private final TaskServiceDB taskServiceDB;
    private final ManualCachingTaskService manualCachingTaskService;
    private final SpringAnnotationCachingTaskService springAnnotationCachingTaskService;
    private final GroupServiceDB groupServiceDB;
    private final ManualCachingGroupService manualCachingGroupService;
    private final SpringAnnotationCachingGroupService springAnnotationCachingGroupService;
    private final UserServiceDB userService;

    @Transactional(readOnly = true)
    public List<TaskGetDto> findAll(CacheMode cacheMode) {
        TaskServiceInterface taskService = resolveTaskService(cacheMode);

        Collection<Task> tasks = taskService.findAll();
        return tasks.stream().map(TaskMapper::toGetDto).toList();
    }

    @Transactional(readOnly = true)
    public List<TaskGetDto> findAllForCurrentUser(CacheMode cacheMode) {
        TaskServiceInterface taskService = resolveTaskService(cacheMode);

        Collection<Task> tasks = taskService.findAllForCurrentUser();
        return tasks.stream().map(TaskMapper::toGetDto).toList();
    }

    @Transactional(readOnly = true)
    public TaskGetDto findById(Long id, CacheMode cacheMode) {
        TaskServiceInterface taskService = resolveTaskService(cacheMode);

        Task task = taskService.findById(id);
        return TaskMapper.toGetDto(task);
    }

    @Transactional(readOnly = true)
    public TaskGetDto findByIdForCurrentUser(Long id, CacheMode cacheMode) {
        TaskServiceInterface taskService = resolveTaskService(cacheMode);

        Task task = taskService.findByIdForCurrentUser(id);
        return TaskMapper.toGetDto(task);
    }

    @Transactional
    public TaskGetBasicDto create(TaskCreateDto dto, CacheMode cacheMode) {
        TaskServiceInterface taskService = resolveTaskService(cacheMode);
        GroupServiceInterface groupService = resolveGroupService(cacheMode);

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
    public TaskGetDto update(Long id, TaskUpdateDto dto, CacheMode cacheMode) {
        TaskServiceInterface taskService = resolveTaskService(cacheMode);
        GroupServiceInterface groupService = resolveGroupService(cacheMode);

        Task task = taskService.findById(id);

        if(!task.getAssignedUser().equals(userService.getCurrentUser())) {
            throw new NotCurrentUserTaskException("it is not your task");
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

    @Transactional
    public TaskGetDto updateStatus(Long id, TaskUserUpdateDto dto, CacheMode cacheMode) {
        TaskServiceInterface taskService = resolveTaskService(cacheMode);

        Task task = taskService.findById(id);
        task.setStatus(dto.getStatus());
        return TaskMapper.toGetDto(taskService.update(task));
    }

    @Transactional
    public void delete(Long id, CacheMode cacheMode) {
        TaskServiceInterface taskService = resolveTaskService(cacheMode);

        taskService.delete(id);
    }

    private TaskServiceInterface resolveTaskService(CacheMode cacheMode) {
        return switch (cacheMode) {
            case NONE_CACHE -> taskServiceDB;
            case MANUAL -> manualCachingTaskService;
            case SPRING -> springAnnotationCachingTaskService;
        };
    }

    private GroupServiceInterface resolveGroupService(CacheMode cacheMode) {
        return switch (cacheMode) {
            case NONE_CACHE -> groupServiceDB;
            case MANUAL -> manualCachingGroupService;
            case SPRING -> springAnnotationCachingGroupService;
        };
    }

}
