package by.kolesnik.springsecuritytms.dto.task;

import by.kolesnik.springsecuritytms.dto.group.GroupGetBasicDto;
import by.kolesnik.springsecuritytms.dto.user.UserGetBasicDto;
import by.kolesnik.springsecuritytms.entity.Group;
import by.kolesnik.springsecuritytms.enums.Priority;
import by.kolesnik.springsecuritytms.enums.Status;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TaskGetDto {

    private Long id;
    private String description;
    private UserGetBasicDto creator;
    private Status status;
    private LocalDateTime createDateTime;
    private LocalDateTime deadlineDate;
    private Priority priority;
    private GroupGetBasicDto group;
    private UserGetBasicDto assignedUser;
}
