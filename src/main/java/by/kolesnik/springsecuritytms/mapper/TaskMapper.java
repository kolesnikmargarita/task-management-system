package by.kolesnik.springsecuritytms.mapper;

import by.kolesnik.springsecuritytms.dto.task.TaskGetBasicDto;
import by.kolesnik.springsecuritytms.dto.task.TaskGetDto;
import by.kolesnik.springsecuritytms.entity.Task;

public class TaskMapper {

    public static TaskGetDto toGetDto(Task entity) {
        TaskGetDto dto = new TaskGetDto();

        dto.setId(entity.getId());
        dto.setDescription(entity.getDescription());
        dto.setGroup(GroupMapper.toGetBasicDto(entity.getGroup()));
        dto.setAssignedUser(UserMapper.toGetBasicDto(entity.getAssignedUser()));
        dto.setPriority(entity.getPriority());
        dto.setStatus(entity.getStatus());
        dto.setCreator(UserMapper.toGetBasicDto(entity.getCreator()));
        dto.setDeadlineDate(entity.getDeadlineDate());
        dto.setCreateDateTime(entity.getCreateDateTime());

        return dto;
    }

    public static TaskGetBasicDto toGetBasicDto(Task entity) {
        TaskGetBasicDto dto = new TaskGetBasicDto();

        dto.setCreateDateTime(entity.getCreateDateTime());
        dto.setId(entity.getId());
        dto.setGroup(GroupMapper.toGetBasicDto(entity.getGroup()));
        dto.setDescription(entity.getDescription());
        dto.setPriority(entity.getPriority());
        dto.setCreator(UserMapper.toGetBasicDto(entity.getCreator()));
        dto.setStatus(entity.getStatus());
        dto.setDeadlineDate(entity.getDeadlineDate());

        return dto;
    }
}
