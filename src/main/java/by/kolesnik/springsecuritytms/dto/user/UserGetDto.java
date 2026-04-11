package by.kolesnik.springsecuritytms.dto.user;

import by.kolesnik.springsecuritytms.dto.group.GroupGetBasicDto;
import by.kolesnik.springsecuritytms.dto.task.TaskGetDto;
import by.kolesnik.springsecuritytms.enums.Role;
import lombok.Data;

import java.util.List;

@Data
public class UserGetDto {

    private Long id;
    private String name;
    private Role role;
    private List<GroupGetBasicDto> groups;
    private List<TaskGetDto> tasks;
}
