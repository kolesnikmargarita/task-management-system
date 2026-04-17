package by.kolesnik.springsecuritytms.facade;

import by.kolesnik.springsecuritytms.dto.group.*;
import by.kolesnik.springsecuritytms.entity.Group;
import by.kolesnik.springsecuritytms.entity.User;
import by.kolesnik.springsecuritytms.mapper.GroupMapper;
import by.kolesnik.springsecuritytms.service.GroupService;
import by.kolesnik.springsecuritytms.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
@RequiredArgsConstructor
public class GroupFacade {

    private final GroupService groupService;
    private final UserService userService;

    public List<GroupGetBasicDto> findAll() {
        Collection<Group> groups = groupService.findAll();
        return groups.stream().map(GroupMapper::toGetBasicDto).toList();
    }

    public GroupGetDto findById(Long id) {
        Group group = groupService.findById(id);
        return GroupMapper.toGetDto(group);
    }

    @Transactional
    public GroupGetBasicDto create(GroupCreateDto dto) {
        Group groupEntity = groupService.create(GroupMapper.toEntity(dto));
        return GroupMapper.toGetBasicDto(groupEntity);
    }

    @Transactional
    public GroupGetBasicDto update(Long id, GroupUpdateDto dto) {
        Group group = groupService.findById(id);

        if(dto.getName() != null) {
            group.setName(dto.getName());
        }

        return GroupMapper.toGetBasicDto(groupService.update(group));
    }

    @Transactional
    public GroupGetDto addUser(Long groupId, GroupUserAddDto userDto) {
        Group group = groupService.findById(groupId);
        ArrayList<User> users = new ArrayList<>(group.getUsers().stream().toList());
        users.add(userService.findById(userDto.getUserId()));
        group.setUsers(users);
        return GroupMapper.toGetDto(groupService.update(group));
    }

    public void delete(Long id) {
        groupService.delete(id);
    }
}