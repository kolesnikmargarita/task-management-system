package by.kolesnik.springsecuritytms.facade;

import by.kolesnik.springsecuritytms.dto.user.UserGetBasicDto;
import by.kolesnik.springsecuritytms.dto.user.UserGetDto;
import by.kolesnik.springsecuritytms.dto.user.UserUpdateDto;
import by.kolesnik.springsecuritytms.entity.User;
import by.kolesnik.springsecuritytms.enums.CacheMode;
import by.kolesnik.springsecuritytms.enums.Role;
import by.kolesnik.springsecuritytms.mapper.UserMapper;
import by.kolesnik.springsecuritytms.service.UserServiceInterface;
import by.kolesnik.springsecuritytms.service.db.UserServiceDB;
import by.kolesnik.springsecuritytms.service.manual.ManualCachingUserService;
import by.kolesnik.springsecuritytms.service.spring.SpringAnnotationCachingUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

@Component
@RequiredArgsConstructor
public class UserFacade {

    private final UserServiceDB userServiceDB;
    private final ManualCachingUserService manualCachingUserService;
    private final SpringAnnotationCachingUserService springAnnotationCachingUserService;

    @Transactional(readOnly = true)
    public List<UserGetBasicDto> findAll(CacheMode cacheMode) {
        UserServiceInterface userService = resolveUserService(cacheMode);

        Collection<User> users = userService.findAll();
        return users.stream().map(UserMapper::toGetBasicDto).toList();
    }

    @Transactional(readOnly = true)
    public UserGetDto findById(Long id, CacheMode cacheMode) {
        UserServiceInterface userService = resolveUserService(cacheMode);

        return UserMapper.toGetDto(userService.findById(id));
    }

    @Transactional
    public UserGetBasicDto update(Long id, UserUpdateDto dto, CacheMode cacheMode) {
        UserServiceInterface userService = resolveUserService(cacheMode);

        User user = userService.findById(id);

        if(dto.getRole() != null) {
            user.setRole(Role.valueOf(dto.getRole()));
        }

        return UserMapper.toGetBasicDto(userService.update(user));
    }

    @Transactional
    public void delete(Long id, CacheMode cacheMode) {
        UserServiceInterface userService = resolveUserService(cacheMode);

        userService.delete(id);
    }

    private UserServiceInterface resolveUserService(CacheMode cacheMode) {
        return switch (cacheMode) {
            case NONE_CACHE -> userServiceDB;
            case MANUAL -> manualCachingUserService;
            case SPRING -> springAnnotationCachingUserService;
        };
    }
}
