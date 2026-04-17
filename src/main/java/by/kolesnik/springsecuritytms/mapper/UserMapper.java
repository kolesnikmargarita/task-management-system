package by.kolesnik.springsecuritytms.mapper;

import by.kolesnik.springsecuritytms.dto.group.GroupGetBasicDto;
import by.kolesnik.springsecuritytms.dto.task.TaskGetDto;
import by.kolesnik.springsecuritytms.dto.user.UserGetBasicDto;
import by.kolesnik.springsecuritytms.dto.user.UserGetDto;
import by.kolesnik.springsecuritytms.entity.User;

import java.util.List;

public class UserMapper {
    public static UserGetBasicDto toGetBasicDto(User entity) {
        UserGetBasicDto dto = new UserGetBasicDto();

        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setRole(entity.getRole());

        return dto;
    }

    public static UserGetDto toGetDto(User entity) {
        UserGetDto dto = new UserGetDto();

        List<TaskGetDto> tasks = entity.getTasks().stream().map(TaskMapper::toGetDto).toList();
        List<GroupGetBasicDto> groups = entity.getGroups().stream().map(GroupMapper::toGetBasicDto).toList();

        dto.setTasks(tasks);
        dto.setGroups(groups);
        dto.setId(entity.getId());
        dto.setRole(entity.getRole());
        dto.setName(entity.getName());

        return dto;
    }
}
