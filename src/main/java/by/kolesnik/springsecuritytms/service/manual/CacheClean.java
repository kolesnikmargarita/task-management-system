package by.kolesnik.springsecuritytms.service.manual;

import by.kolesnik.springsecuritytms.entity.Group;
import by.kolesnik.springsecuritytms.entity.Task;
import by.kolesnik.springsecuritytms.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Slf4j
@Component
@RequiredArgsConstructor
public class CacheClean {

    private final RedisTemplate<String, Group> groupRedisTemplate;
    private final RedisTemplate<String, User> userRedisTemplate;
    private final RedisTemplate<String, Task> taskRedisTemplate;

    public void cleanConnectedTasksAndUsersForGroup(Group group) {
        Collection<Task> tasks = group.getTasks();
        for(Task task : tasks) {
            String taskCacheKey = ManualCachingTaskService.CACHE_KEY_PREFIX + task.getId();
            taskRedisTemplate.delete(taskCacheKey);
            log.info("Task of edit group is deleted: id={}", task.getId());
        }

        Collection<User> users = group.getUsers();
        for(User user : users) {
            String userCacheKey = ManualCachingUserService.CACHE_KEY_PREFIX + user.getId();
            userRedisTemplate.delete(userCacheKey);
            log.info("Users of edit group is deleted: id={}", user.getId());
        }
    }

    public void cleanConnectedGroupAndUserForTask(Task task) {
        Group group = task.getGroup();
        String groupCacheKey = ManualCachingGroupService.CACHE_KEY_PREFIX + group.getId();
        taskRedisTemplate.delete(groupCacheKey);
        log.info("Group of edit task is deleted: id={}", group.getId());

        User assignedUser = task.getAssignedUser();
        String userCacheKey = ManualCachingUserService.CACHE_KEY_PREFIX + assignedUser.getId();
        userRedisTemplate.delete(userCacheKey);
        log.info("User of edit task is deleted: id={}", assignedUser.getId());
    }

    public void cleanConnectedGroupsAndTasksForUser(User user) {
        Collection<Group> groups = user.getGroups();
        for(Group group : groups) {
            String groupCacheKey = ManualCachingGroupService.CACHE_KEY_PREFIX + group.getId();
            groupRedisTemplate.delete(groupCacheKey);
            log.info("Groups of edit user is deleted: id={}", group.getId());
        }

        Collection<Task> tasks = user.getTasks();
        for(Task task : tasks) {
            String taskCacheKey = ManualCachingTaskService.CACHE_KEY_PREFIX + task.getId();
            taskRedisTemplate.delete(taskCacheKey);
            log.info("Task of edit user is deleted: id={}", task.getId());
        }
    }
}
