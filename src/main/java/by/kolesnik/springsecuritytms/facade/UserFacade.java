package by.kolesnik.springsecuritytms.facade;

import by.kolesnik.springsecuritytms.dto.user.UserGetBasicDto;
import by.kolesnik.springsecuritytms.dto.user.UserGetDto;
import by.kolesnik.springsecuritytms.dto.user.UserUpdateDto;
import by.kolesnik.springsecuritytms.entity.User;
import by.kolesnik.springsecuritytms.enums.Role;
import by.kolesnik.springsecuritytms.mapper.UserMapper;
import by.kolesnik.springsecuritytms.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

@Component
@RequiredArgsConstructor
public class UserFacade {

    private final UserService userService;

    public List<UserGetBasicDto> findAll() {
        Collection<User> users = userService.findAll();
        return users.stream().map(UserMapper::toGetBasicDto).toList();
    }

    public UserGetDto findById(Long id) {
        return UserMapper.toGetDto(userService.findById(id));
    }

    @Transactional
    public UserGetBasicDto update(Long id, UserUpdateDto dto) {
        User user = userService.findById(id);

        if(dto.getRole() != null) {
            user.setRole(Role.valueOf(dto.getRole()));
        }

        return UserMapper.toGetBasicDto(userService.update(user));
    }

    public void delete(Long id) {
        userService.delete(id);
    }
}
