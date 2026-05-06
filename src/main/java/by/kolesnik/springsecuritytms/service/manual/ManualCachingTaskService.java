package by.kolesnik.springsecuritytms.service.manual;

import by.kolesnik.springsecuritytms.entity.Task;
import by.kolesnik.springsecuritytms.exception.DeadlineInPastException;
import by.kolesnik.springsecuritytms.exception.NotCurrentUserTaskException;
import by.kolesnik.springsecuritytms.repository.TaskRepository;
import by.kolesnik.springsecuritytms.service.TaskServiceInterface;
import by.kolesnik.springsecuritytms.service.db.UserServiceDB;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class ManualCachingTaskService implements TaskServiceInterface {

    private final TaskRepository taskRepository;
    private final UserServiceDB userService;
    private final RedisTemplate<String, Task> redisTemplate;
    private final CacheClean cacheClean;

    static final String CACHE_KEY_PREFIX = "task:";
    private static final long CACHE_TTL_MINUTES = 1;


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
        String cacheKey = CACHE_KEY_PREFIX + id;
        Task objectFromCache = redisTemplate.opsForValue().get(cacheKey);


        if(objectFromCache != null) {
            log.info("Task found in cache: id={}", id);
            return objectFromCache;
        }

        log.info("Task not found in cache: id={}", id);
        Optional<Task> optionalTaskFromDB = taskRepository.findById(id);

        if(optionalTaskFromDB.isEmpty()) {
            throw new EntityNotFoundException("task with id=" + id + " not found");
        }

        redisTemplate.opsForValue()
                .set(cacheKey, optionalTaskFromDB.get(), CACHE_TTL_MINUTES, TimeUnit.MINUTES);

        log.info("Task cached: id={}", id);

        return optionalTaskFromDB.get();
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

        Task savedTask = taskRepository.save(task);

        String cacheKey = CACHE_KEY_PREFIX + savedTask.getId();
        redisTemplate.delete(cacheKey);
        log.info("Id deleted from cache for updated group: id={}", savedTask.getId());

        cacheClean.cleanConnectedGroupAndUserForTask(savedTask);

        return savedTask;
    }

    @Override
    public void delete(Long id) {
        Task task = findById(id);
        taskRepository.deleteById(id);

        String cacheKey = CACHE_KEY_PREFIX + id;
        redisTemplate.delete(cacheKey);
        log.info("Task id deleted from cache: id={}", id);

        cacheClean.cleanConnectedGroupAndUserForTask(task);
    }
}
