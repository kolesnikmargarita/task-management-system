package by.kolesnik.springsecuritytms.dto.group;

import by.kolesnik.springsecuritytms.dto.task.TaskGetDto;
import by.kolesnik.springsecuritytms.dto.user.UserGetBasicDto;
import lombok.Data;

import java.util.List;

@Data
public class GroupGetDto {

    private Long id;
    private String name;
    private List<TaskGetDto> tasks;
    private List<UserGetBasicDto> users;
}
