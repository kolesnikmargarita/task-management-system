package by.kolesnik.springsecuritytms.controller;

import by.kolesnik.springsecuritytms.dto.group.*;
import by.kolesnik.springsecuritytms.facade.GroupFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/groups")
@RequiredArgsConstructor
public class GroupController {

    private final GroupFacade groupFacade;

    @GetMapping
    public List<GroupGetBasicDto> findAll() {
        return groupFacade.findAll();
    }

    @GetMapping("/{id}")
    public GroupGetDto findById(@PathVariable Long id) {
        return groupFacade.findById(id);
    }

    @PostMapping
    public GroupGetBasicDto create(@RequestBody GroupCreateDto dto) {
        return groupFacade.create(dto);
    }

    @PatchMapping("/{id}")
    public GroupGetBasicDto update(@PathVariable Long id, @RequestBody GroupUpdateDto dto) {
        return groupFacade.update(id, dto);
    }

    @PatchMapping("/{id}/add_user")
    public GroupGetDto addUser(@PathVariable Long id, @RequestBody GroupUserAddDto userAddDto) {
        return groupFacade.addUser(id, userAddDto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        groupFacade.delete(id);
    }
}
