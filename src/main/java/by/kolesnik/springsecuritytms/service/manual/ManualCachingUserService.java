package by.kolesnik.springsecuritytms.service.manual;

import by.kolesnik.springsecuritytms.entity.Group;
import by.kolesnik.springsecuritytms.entity.User;
import by.kolesnik.springsecuritytms.enums.Role;
import by.kolesnik.springsecuritytms.repository.UserRepository;
import by.kolesnik.springsecuritytms.service.UserServiceInterface;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class ManualCachingUserService implements UserServiceInterface {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final CacheClean cacheClean;

    private final RedisTemplate<String, User> redisTemplate;

    static final String CACHE_KEY_PREFIX = "user:";
    private static final long CACHE_TTL_MINUTES = 1;

    @Override
    public User createUser(String username, String password, String name) {
        final User user = new User();
        user.setName(name);
        user.setUsername(username);
        final String passwordHash = passwordEncoder.encode(password);
        user.setPassword(passwordHash);
        Collection<User> users = findAll();
        if(findAll().isEmpty()) {
            user.setRole(Role.ROLE_ADMIN);
        }
        return userRepository.save(user);
    }

    @Override
    public User getCurrentUser() {
        final String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<User> optionalUser = userRepository.findByUsername(username);

        if(optionalUser.isEmpty()) {
            throw new EntityNotFoundException("current user not found");
        }

        return optionalUser.get();
    }

    @Override
    public Collection<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findById(Long id) {
        String cacheKey = CACHE_KEY_PREFIX + id;
        User objectFromCache = redisTemplate.opsForValue().get(cacheKey);

        if(objectFromCache != null) {
            log.info("User found in cache: id={}", id);
            return objectFromCache;
        }
        log.info("User not found in cache: id={}", id);

        Optional<User> optionalUserFromDB = userRepository.findById(id);

        if(optionalUserFromDB.isEmpty()) {
            throw new EntityNotFoundException("user with id=" + id + " not found");
        }

        redisTemplate.opsForValue()
                .set(cacheKey, optionalUserFromDB.get(), CACHE_TTL_MINUTES, TimeUnit.MINUTES);

        log.info("User cached: id={}", id);

        return optionalUserFromDB.get();
    }

    @Override
    public User findByIdAndGroupId(Long id, Group group) {
        Optional<User> optionalUser = userRepository.findByIdAndGroups(id, group);

        if(optionalUser.isEmpty()) {
            throw new EntityNotFoundException("user with id=" + id + " not found in this group");
        }

        return optionalUser.get();
    }

    @Override
    public User update(User user) {
        User savedUser = userRepository.save(user);

        String cacheKey = CACHE_KEY_PREFIX + savedUser.getId();
        redisTemplate.delete(cacheKey);
        log.info("Id deleted from cache for updated user: id={}", savedUser.getId());

        cacheClean.cleanConnectedGroupsAndTasksForUser(savedUser);

        return savedUser;
    }

    @Override
    public void delete(Long id) {
        User deletedUser = findById(id);
        userRepository.deleteById(id);

        String cacheKey = CACHE_KEY_PREFIX + id;
        redisTemplate.delete(cacheKey);
        log.info("User id deleted from cache: id={}", id);

        cacheClean.cleanConnectedGroupsAndTasksForUser(deletedUser);
    }
}
