package by.kolesnik.springsecuritytms.facade;

import by.kolesnik.springsecuritytms.dto.group.*;
import by.kolesnik.springsecuritytms.entity.Group;
import by.kolesnik.springsecuritytms.entity.User;
import by.kolesnik.springsecuritytms.enums.CacheMode;
import by.kolesnik.springsecuritytms.mapper.GroupMapper;
import by.kolesnik.springsecuritytms.service.GroupServiceInterface;
import by.kolesnik.springsecuritytms.service.db.GroupServiceDB;
import by.kolesnik.springsecuritytms.service.db.UserServiceDB;
import by.kolesnik.springsecuritytms.service.manual.ManualCachingGroupService;
import by.kolesnik.springsecuritytms.service.spring.SpringAnnotationCachingGroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
@RequiredArgsConstructor
public class GroupFacade {

    private final GroupServiceDB groupServiceDB;
    private final ManualCachingGroupService manualCachingGroupService;
    private final SpringAnnotationCachingGroupService springAnnotationCachingGroupService;
    private final UserServiceDB userService;

    @Transactional(readOnly = true)
    public List<GroupGetBasicDto> findAll(CacheMode cacheMode) {
        GroupServiceInterface groupService = resolveGroupService(cacheMode);

        Collection<Group> groups = groupService.findAll();
        return groups.stream().map(GroupMapper::toGetBasicDto).toList();
    }

    @Transactional(readOnly = true)
    public GroupGetDto findById(Long id, CacheMode cacheMode) {
        GroupServiceInterface groupService = resolveGroupService(cacheMode);

        Group group = groupService.findById(id);
        return GroupMapper.toGetDto(group);
    }

    @Transactional
    public GroupGetBasicDto create(GroupCreateDto dto, CacheMode cacheMode) {
        GroupServiceInterface groupService = resolveGroupService(cacheMode);

        Group groupEntity = groupService.create(GroupMapper.toEntity(dto));
        return GroupMapper.toGetBasicDto(groupEntity);
    }

    @Transactional
    public GroupGetBasicDto update(Long id, GroupUpdateDto dto, CacheMode cacheMode) {
        GroupServiceInterface groupService = resolveGroupService(cacheMode);

        Group group = groupService.findById(id);

        if(dto.getName() != null) {
            group.setName(dto.getName());
        }

        return GroupMapper.toGetBasicDto(groupService.update(group));
    }

    @Transactional
    public GroupGetDto addUser(Long groupId, GroupUserAddDto userDto, CacheMode cacheMode) {
        GroupServiceInterface groupService = resolveGroupService(cacheMode);

        Group group = groupService.findById(groupId);
        ArrayList<User> users = new ArrayList<>(group.getUsers().stream().toList());
        users.add(userService.findById(userDto.getUserId()));
        group.setUsers(users);
        return GroupMapper.toGetDto(groupService.update(group));
    }

    @Transactional
    public void delete(Long id, CacheMode cacheMode) {
        GroupServiceInterface groupService = resolveGroupService(cacheMode);

        groupService.delete(id);
    }

    private GroupServiceInterface resolveGroupService(CacheMode cacheMode) {
        return switch (cacheMode) {
            case NONE_CACHE -> groupServiceDB;
            case MANUAL -> manualCachingGroupService;
            case SPRING -> springAnnotationCachingGroupService;
        };
    }
}