package by.kolesnik.springsecuritytms.service.manual;

import by.kolesnik.springsecuritytms.entity.Group;
import by.kolesnik.springsecuritytms.repository.GroupRepository;
import by.kolesnik.springsecuritytms.service.GroupServiceInterface;
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
public class ManualCachingGroupService implements GroupServiceInterface {

    private final GroupRepository groupRepository;
    private final RedisTemplate<String, Group> redisTemplate;
    private final CacheClean cacheClean;

    static final String CACHE_KEY_PREFIX = "group:";
    private static final long CACHE_TTL_MINUTES = 1;

    @Override
    public Collection<Group> findAll() {
        return groupRepository.findAll();
    }

    @Override
    public Group findById(Long id) {
        String cacheKey = CACHE_KEY_PREFIX + id;
        Group objectFromCache = redisTemplate.opsForValue().get(cacheKey);

        if(objectFromCache != null) {
            log.info("Group found in cache: id={}", id);
            return objectFromCache;
        }

        log.info("Group not found in cache: id={}", id);
        Optional<Group> optionalGroupFromDB = groupRepository.findById(id);

        if(optionalGroupFromDB.isEmpty()) {
            throw new EntityNotFoundException("group with id=" + id + " not found");
        }

        redisTemplate.opsForValue()
                .set(cacheKey, optionalGroupFromDB.get(), CACHE_TTL_MINUTES, TimeUnit.MINUTES);

        log.info("Group cached: id={}", id);

        return optionalGroupFromDB.get();
    }

    @Override
    public Group create(Group group) {
        return groupRepository.save(group);
    }

    @Override
    public Group update(Group group) {
        Group savedGroup = groupRepository.save(group);

        String cacheKey = CACHE_KEY_PREFIX + savedGroup.getId();
        redisTemplate.delete(cacheKey);
        log.info("Id deleted from cache for updated group: id={}", savedGroup.getId());

        cacheClean.cleanConnectedTasksAndUsersForGroup(savedGroup);

        return savedGroup;
    }

    @Override
    public void delete(Long id) {
        Group deletedGroup = findById(id);
        groupRepository.deleteById(id);

        if(deletedGroup != null){
            String cacheKey = CACHE_KEY_PREFIX + id;
            redisTemplate.delete(cacheKey);
            log.info("Group id deleted from cache: id={}", id);

            cacheClean.cleanConnectedTasksAndUsersForGroup(deletedGroup);
        }
    }
}
