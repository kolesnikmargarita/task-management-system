package by.kolesnik.springsecuritytms.locking;

import by.kolesnik.springsecuritytms.dto.group.GroupGetBasicDto;
import by.kolesnik.springsecuritytms.dto.group.GroupUpdateDto;
import by.kolesnik.springsecuritytms.enums.CacheMode;
import by.kolesnik.springsecuritytms.facade.GroupFacade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.Duration;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/groups/lock")
public class GroupUpdateLockingController {

    private final RedisLockManager redisLockManager;
    private final GroupFacade groupFacade;

    public GroupGetBasicDto update(
            @PathVariable Long id,
            @RequestBody GroupUpdateDto dto,
            @RequestParam(value = "cacheMode", defaultValue = "NONE_CACHE") CacheMode cacheMode,
            @RequestParam(value = "workMs", defaultValue = "500") long workMs) {
        String lockKey = "group:" + id;

        String lockId = redisLockManager.tryLock(lockKey, Duration.ofMinutes(1));
        if(lockId == null) {
            throw new ResponseStatusException(
                    HttpStatus.LOCKED,
                    "Lock acquired on object %s. Try again later.".formatted(lockKey)
            );
        }

        try {
            try {
                Thread.sleep(workMs);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            log.info("Group updating: id={}", id);
            return groupFacade.update(id, dto, cacheMode);
        } finally {
            redisLockManager.unlockLock(lockKey, lockId);
        }
    }
}
