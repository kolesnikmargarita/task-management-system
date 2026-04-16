package by.kolesnik.springsecuritytms.controller;

import by.kolesnik.springsecuritytms.controller.openapi.GroupOpenApi;
import by.kolesnik.springsecuritytms.dto.group.*;
import by.kolesnik.springsecuritytms.facade.GroupFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/groups")
@RequiredArgsConstructor
public class GroupController implements GroupOpenApi {

    private final GroupFacade groupFacade;

    @GetMapping
    @Override
    public List<GroupGetBasicDto> findAll() {
        return groupFacade.findAll();
    }

    @GetMapping("/{id}")
    @Override
    public GroupGetDto findById(@PathVariable Long id) {
        return groupFacade.findById(id);
    }

    @PostMapping
    @Override
    public GroupGetBasicDto create(@RequestBody GroupCreateDto dto) {
        return groupFacade.create(dto);
    }

    @PatchMapping("/{id}")
    @Override
    public GroupGetBasicDto update(@PathVariable Long id, @RequestBody GroupUpdateDto dto) {
        return groupFacade.update(id, dto);
    }

    @PatchMapping("/{id}/add_user")
    @Override
    public GroupGetDto addUser(@PathVariable Long id, @RequestBody GroupUserAddDto userAddDto) {
        return groupFacade.addUser(id, userAddDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Override
    public void delete(@PathVariable Long id) {
        groupFacade.delete(id);
    }
}
