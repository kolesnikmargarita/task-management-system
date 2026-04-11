package by.kolesnik.springsecuritytms.controller;

import by.kolesnik.springsecuritytms.dto.user.UserGetBasicDto;
import by.kolesnik.springsecuritytms.dto.user.UserGetDto;
import by.kolesnik.springsecuritytms.dto.user.UserUpdateDto;
import by.kolesnik.springsecuritytms.facade.UserFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserFacade userFacade;

    @GetMapping
    public List<UserGetBasicDto> findAll() {
        // todo: get id, name, email, role for all users
        return userFacade.findAll();
    }

    @GetMapping("/{id}")
    public UserGetDto findById(@PathVariable Long id) {
        // todo: get id, name, email, groups, tasks for selected user
        return userFacade.findById(id);
    }

    @PatchMapping("/{id}")
    public UserGetBasicDto update(@PathVariable Long id, @RequestBody UserUpdateDto dto) {
        // todo: update role
        return userFacade.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        // todo: delete selected user
        userFacade.delete(id);
    }
}
