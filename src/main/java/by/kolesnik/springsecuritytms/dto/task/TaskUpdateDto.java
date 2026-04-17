package by.kolesnik.springsecuritytms.dto.task;

import by.kolesnik.springsecuritytms.dto.user.UserGetBasicDto;
import by.kolesnik.springsecuritytms.enums.Priority;
import by.kolesnik.springsecuritytms.enums.Status;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TaskUpdateDto {

    private String description;
    private Status status;
    private LocalDateTime deadlineDate;
    private Priority priority;
    private Long groupId;
    private Long assignedUserId;
}
