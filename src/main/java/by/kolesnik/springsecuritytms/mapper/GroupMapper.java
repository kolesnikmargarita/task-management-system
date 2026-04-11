package by.kolesnik.springsecuritytms.mapper;

import by.kolesnik.springsecuritytms.dto.group.GroupCreateDto;
import by.kolesnik.springsecuritytms.dto.group.GroupGetBasicDto;
import by.kolesnik.springsecuritytms.dto.group.GroupGetDto;
import by.kolesnik.springsecuritytms.dto.group.GroupUpdateDto;
import by.kolesnik.springsecuritytms.dto.task.TaskGetDto;
import by.kolesnik.springsecuritytms.dto.user.UserGetBasicDto;
import by.kolesnik.springsecuritytms.entity.Group;
import by.kolesnik.springsecuritytms.entity.Task;
import by.kolesnik.springsecuritytms.entity.User;

import java.util.Collection;
import java.util.List;

public class GroupMapper {

    //private final TaskMapper taskMapper = new TaskMapper();

    public static GroupGetBasicDto toGetBasicDto(Group entity) {

        GroupGetBasicDto dto = new GroupGetBasicDto();

        dto.setId(entity.getId());
        dto.setName(entity.getName());

        return dto;
    }

    public static GroupGetDto toGetDto(Group entity) {
        GroupGetDto dto = new GroupGetDto();

        Collection<Task> tasks = entity.getTasks();
        List<TaskGetDto> dtoTasks = tasks.stream().map(TaskMapper::toGetDto).toList();

        Collection<User> users = entity.getUsers();
        List<UserGetBasicDto> dtoUsers = users.stream().map(UserMapper::toGetBasicDto).toList();

        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setTasks(dtoTasks);
        dto.setUsers(dtoUsers);

        return dto;
    }

    public static Group toEntity(GroupCreateDto dto) {
        Group group = new Group();

        group.setName(dto.getName());

        return group;
    }
}
