package by.kolesnik.springsecuritytms.dto.task;

import by.kolesnik.springsecuritytms.dto.group.GroupGetBasicDto;
import by.kolesnik.springsecuritytms.enums.Priority;
import by.kolesnik.springsecuritytms.enums.Status;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TaskCreateDto {

    private String description;
    private LocalDateTime deadlineDate;
    private Priority priority;
    private Long groupId;
    private Long assignedUserId;
}
